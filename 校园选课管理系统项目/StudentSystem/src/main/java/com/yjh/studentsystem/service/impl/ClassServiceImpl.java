package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.dto.ClassGenerationDTO;
import com.yjh.studentsystem.entity.Class;
import com.yjh.studentsystem.entity.Course;
import com.yjh.studentsystem.entity.Student;
import com.yjh.studentsystem.entity.StudentClass;
import com.yjh.studentsystem.mapper.ClassMapper;
import com.yjh.studentsystem.mapper.ClassroomMapper;
import com.yjh.studentsystem.mapper.StudentClassMapper;
import com.yjh.studentsystem.mapper.StudentCourseMapper;
import com.yjh.studentsystem.service.ClassService;
import com.yjh.studentsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 班级服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    private final CourseService courseService;
    private final StudentCourseMapper studentCourseMapper;
    private final ClassroomMapper classroomMapper;
    private final StudentClassMapper studentClassMapper;

    @Override
    public List<Class> getClassesByCourseAndTerm(Long courseId, Long termId) {
        return baseMapper.getClassesByCourseAndTerm(courseId, termId);
    }

    @Override
    public Class getClassDetail(Long id) {
        return baseMapper.getClassWithDetails(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> generateClasses(ClassGenerationDTO generationDTO) {
        List<Long> courseIds = generationDTO.getCourseIds();
        Long termId = generationDTO.getTermId();
        Integer maxClassSize = generationDTO.getMaxClassSize();
        Boolean groupByMajor = generationDTO.getGroupByMajor();

        List<Long> generatedClassIds = new ArrayList<>();

        for (Long courseId : courseIds) {
            // 查询课程信息
            Course course = courseService.getById(courseId);
            if (course == null) {
                log.warn("课程不存在：{}", courseId);
                continue;
            }

            // 查询已选该课程的学生列表
            List<Long> studentIds = studentCourseMapper.getSelectedStudentIds(courseId, termId, 1);
            if (studentIds == null || studentIds.isEmpty()) {
                log.warn("课程无学生选课：{}", courseId);
                continue;
            }

            // 按专业分组
            Map<String, List<Long>> majorGroups = new HashMap<>();
            if (groupByMajor) {
                // 按专业分组学生
                majorGroups = groupStudentsByMajor(studentIds);
            } else {
                // 不分组，所有学生放一起
                majorGroups.put("ALL", studentIds);
            }

            // 为每个专业组生成班级
            for (Map.Entry<String, List<Long>> entry : majorGroups.entrySet()) {
                String majorName = entry.getKey();
                List<Long> majorStudentIds = entry.getValue();

                // 计算需要的班级数量
                int totalStudents = majorStudentIds.size();
                int classCount = (int) Math.ceil((double) totalStudents / maxClassSize);

                for (int i = 0; i < classCount; i++) {
                    // 计算当前班级的学生范围
                    int startIdx = i * maxClassSize;
                    int endIdx = Math.min((i + 1) * maxClassSize, totalStudents);
                    List<Long> classStudentIds = majorStudentIds.subList(startIdx, endIdx);

                    // 生成班级名称
                    String className = generateClassName(course.getCourseName(), majorName, i + 1);

                    // 查找可用教室
                    Long classroomId = findAvailableClassroom(classStudentIds.size());
                    if (classroomId == null) {
                        throw new BusinessException("没有足够容量的可用教室");
                    }

                    // 创建班级记录
                    Class clazz = new Class();
                    clazz.setCourseId(courseId);
                    clazz.setClassName(className);
                    clazz.setTeacherId(course.getTeacherId());
                    clazz.setClassroomId(classroomId);
                    clazz.setTermId(termId);
                    clazz.setStudentCount(classStudentIds.size());

                    // 保存班级记录
                    save(clazz);
                    generatedClassIds.add(clazz.getId());

                    // 创建学生班级关联
                    createStudentClassRelations(classStudentIds, clazz.getId(), courseId, termId);
                }
            }
        }

        return generatedClassIds;
    }

    @Override
    public boolean updateStudentCount(Long classId, int count) {
        return baseMapper.updateStudentCount(classId, count) > 0;
    }

    /**
     * 按专业分组学生
     *
     * @param studentIds 学生ID列表
     * @return 专业-学生ID列表 映射
     */
    private Map<String, List<Long>> groupStudentsByMajor(List<Long> studentIds) {
        Map<String, List<Long>> majorGroups = new HashMap<>();

        // 获取学生详细信息
        List<Student> students = new ArrayList<>();
        // 这里简化了实现，实际需要根据studentIds批量查询学生信息
        // students = studentService.listByIds(studentIds);

        for (Student student : students) {
            String major = student.getMajor();
            if (major == null || major.isEmpty()) {
                major = "其他";
            }

            // 将学生添加到对应专业的列表中
            majorGroups.computeIfAbsent(major, k -> new ArrayList<>()).add(student.getId());
        }

        return majorGroups;
    }

    /**
     * 生成班级名称
     *
     * @param courseName 课程名称
     * @param majorName  专业名称
     * @param index      班级序号
     * @return 班级名称
     */
    private String generateClassName(String courseName, String majorName, int index) {
        if ("ALL".equals(majorName)) {
            return courseName + " " + index + "班";
        } else {
            return courseName + " " + majorName + " " + index + "班";
        }
    }

    /**
     * 查找可用教室
     *
     * @param studentCount 学生数量
     * @return 教室ID
     */
    private Long findAvailableClassroom(int studentCount) {
        List<com.yjh.studentsystem.entity.Classroom> classrooms = classroomMapper.getAvailableClassrooms(studentCount);
        if (classrooms != null && !classrooms.isEmpty()) {
            return classrooms.get(0).getId();
        }
        return null;
    }

    /**
     * 创建学生班级关联
     *
     * @param studentIds 学生ID列表
     * @param classId    班级ID
     * @param courseId   课程ID
     * @param termId     学期ID
     */
    private void createStudentClassRelations(List<Long> studentIds, Long classId, Long courseId, Long termId) {
        List<StudentClass> studentClasses = studentIds.stream().map(studentId -> {
            StudentClass studentClass = new StudentClass();
            studentClass.setStudentId(studentId);
            studentClass.setClassId(classId);
            studentClass.setCourseId(courseId);
            studentClass.setTermId(termId);
            return studentClass;
        }).collect(Collectors.toList());

        if (!studentClasses.isEmpty()) {
            studentClassMapper.batchInsert(studentClasses);
        }
    }
} 