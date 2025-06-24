package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.common.Constants;
import com.yjh.studentsystem.dto.CourseSelectionDTO;
import com.yjh.studentsystem.entity.Class;
import com.yjh.studentsystem.entity.Course;
import com.yjh.studentsystem.entity.StudentCourse;
import com.yjh.studentsystem.mapper.CourseMapper;
import com.yjh.studentsystem.mapper.CourseScheduleMapper;
import com.yjh.studentsystem.mapper.PrerequisiteMapper;
import com.yjh.studentsystem.mapper.StudentCourseMapper;
import com.yjh.studentsystem.service.ClassService;
import com.yjh.studentsystem.service.CourseSelectionService;
import com.yjh.studentsystem.service.CourseService;
import com.yjh.studentsystem.service.TermService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程选择服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseSelectionServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements CourseSelectionService {

    private final CourseService courseService;
    private final ClassService classService;
    private final TermService termService;
    private final CourseMapper courseMapper;
    private final PrerequisiteMapper prerequisiteMapper;
    private final CourseScheduleMapper courseScheduleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean selectCourse(CourseSelectionDTO selectionDTO) {
        Long studentId = selectionDTO.getStudentId();
        Long courseId = selectionDTO.getCourseId();
        Long termId = selectionDTO.getTermId();

        // 检查是否在选课时间内
        if (!termService.isSelectionTime(termId)) {
            throw new BusinessException("当前不在选课时间内");
        }

        // 检查是否已选课程
        if (baseMapper.checkStudentCourse(studentId, courseId, termId) > 0) {
            throw new BusinessException("已选择该课程");
        }

        // 检查先修课是否满足
        if (!checkPrerequisite(studentId, courseId)) {
            throw new BusinessException("先修课未通过，无法选课");
        }

        // 检查课程是否有剩余名额
        if (!checkCourseAvailable(courseId)) {
            throw new BusinessException("课程已满员");
        }

        // 检查课程时间是否冲突
        if (checkTimeConflict(studentId, courseId, termId)) {
            throw new BusinessException("课程时间冲突");
        }

        // 获取课程所在班级（选择当前人数最少的班级）
        List<Class> classes = classService.getClassesByCourseAndTerm(courseId, termId);
        if (classes == null || classes.isEmpty()) {
            throw new BusinessException("课程暂未开班，无法选课");
        }

        // 选择当前学生数最少的班级
        Class clazz = classes.stream()
                .min((c1, c2) -> Integer.compare(c1.getStudentCount(), c2.getStudentCount()))
                .orElse(null);
        if (clazz == null) {
            throw new BusinessException("选班失败，请稍后重试");
        }

        // 创建选课记录
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        studentCourse.setClassId(clazz.getId());
        studentCourse.setTermId(termId);
        studentCourse.setStatus(Constants.SELECTION_STATUS_SELECTED);
        studentCourse.setApplyTime(LocalDateTime.now());

        // 保存选课记录
        boolean result = save(studentCourse);
        if (!result) {
            throw new BusinessException("选课失败，请稍后重试");
        }

        // 更新课程选课人数
        courseService.updateCurrentStudent(courseId, 1);

        // 更新班级学生数量
        classService.updateStudentCount(clazz.getId(), 1);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean withdrawCourse(Long selectionId) {
        // 查询选课记录
        StudentCourse studentCourse = getById(selectionId);
        if (studentCourse == null) {
            throw new BusinessException("选课记录不存在");
        }

        // 检查是否已退课
        if (studentCourse.getStatus() == Constants.SELECTION_STATUS_WITHDRAWN) {
            throw new BusinessException("已退课，请勿重复操作");
        }

        // 检查是否在退课时间内
        if (!termService.isWithdrawTime(studentCourse.getTermId())) {
            throw new BusinessException("当前不在退课时间内");
        }

        // 更新选课状态
        studentCourse.setStatus(Constants.SELECTION_STATUS_WITHDRAWN);
        boolean result = updateById(studentCourse);
        if (!result) {
            throw new BusinessException("退课失败，请稍后重试");
        }

        // 更新课程选课人数
        courseService.updateCurrentStudent(studentCourse.getCourseId(), -1);

        // 更新班级学生数量
        classService.updateStudentCount(studentCourse.getClassId(), -1);

        return true;
    }

    @Override
    public IPage<StudentCourse> pageStudentCourses(Long studentId, Long termId, Integer status, Integer pageNum, Integer pageSize) {
        IPage<StudentCourse> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectStudentCoursePage(page, studentId, termId, status);
    }

    @Override
    public StudentCourse getStudentCourseDetail(Long id) {
        return baseMapper.getStudentCourseWithDetails(id);
    }

    @Override
    public boolean checkPrerequisite(Long studentId, Long courseId) {
        // 获取先修课ID列表
        List<Long> prerequisiteCourseIds = prerequisiteMapper.getPrerequisiteCourseIds(courseId);
        if (prerequisiteCourseIds == null || prerequisiteCourseIds.isEmpty()) {
            return true;
        }

        // 检查是否通过所有先修课
        for (Long prerequisiteCourseId : prerequisiteCourseIds) {
            if (baseMapper.checkCoursePass(studentId, prerequisiteCourseId) <= 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean checkCourseAvailable(Long courseId) {
        Course course = courseMapper.selectById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        return course.getStatus() == 1 && course.getCurrentStudent() < course.getMaxStudent();
    }

    @Override
    public boolean checkTimeConflict(Long studentId, Long courseId, Long termId) {
        // 获取目标课程的课程时间表
        List<com.yjh.studentsystem.entity.CourseSchedule> schedules = courseScheduleMapper.getSchedulesByCourseAndTerm(courseId, termId);
        if (schedules == null || schedules.isEmpty()) {
            return false;
        }

        // 检查每个时间段是否冲突
        for (com.yjh.studentsystem.entity.CourseSchedule schedule : schedules) {
            // 转换时间格式
            String startTime = schedule.getStartTime().toString();
            String endTime = schedule.getEndTime().toString();

            // 查询时间冲突的课程
            List<Long> conflictCourseIds = courseScheduleMapper.getTimeConflictCourses(
                    studentId, termId, schedule.getDayOfWeek(), startTime, endTime, schedule.getWeekRange());

            if (!conflictCourseIds.isEmpty()) {
                return true;
            }
        }

        return false;
    }
} 