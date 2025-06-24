package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.common.Constants;
import com.yjh.studentsystem.dto.CourseDetailDTO;
import com.yjh.studentsystem.dto.CourseQueryDTO;
import com.yjh.studentsystem.entity.Course;
import com.yjh.studentsystem.entity.CourseSchedule;
import com.yjh.studentsystem.entity.Prerequisite;
import com.yjh.studentsystem.mapper.CourseMapper;
import com.yjh.studentsystem.mapper.CourseScheduleMapper;
import com.yjh.studentsystem.mapper.PrerequisiteMapper;
import com.yjh.studentsystem.mapper.StudentCourseMapper;
import com.yjh.studentsystem.service.CourseService;
import com.yjh.studentsystem.service.TermService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private final PrerequisiteMapper prerequisiteMapper;
    private final CourseScheduleMapper courseScheduleMapper;
    private final StudentCourseMapper studentCourseMapper;
    private final TermService termService;

    @Override
    public IPage<Course> pageCourses(CourseQueryDTO query) {
        IPage<Course> page = new Page<>(query.getPageNum(), query.getPageSize());
        return baseMapper.selectCoursePage(page, query);
    }

    @Override
    public CourseDetailDTO getCourseDetail(Long id) {
        // 查询课程信息
        Course course = baseMapper.getCourseWithTeacher(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        // 查询先修课程
        List<Course> prerequisiteCourses = baseMapper.getPrerequisiteCourses(id);
        course.setPrerequisiteCourses(prerequisiteCourses);

        // 查询课程时间表
        Long termId = termService.getCurrentTerm().getId();
        List<CourseSchedule> schedules = courseScheduleMapper.getSchedulesByCourseAndTerm(id, termId);
        course.setSchedules(schedules);

        // 转换为DTO
        CourseDetailDTO detailDTO = new CourseDetailDTO();
        BeanUtils.copyProperties(course, detailDTO);

        // 设置剩余名额
        detailDTO.setRemainingPlaces(course.getMaxStudent() - course.getCurrentStudent());

        // 设置课程类型名称
        detailDTO.setCourseTypeName(course.getCourseType() == Constants.COURSE_TYPE_REQUIRED ? "必修" : "选修");

        // 设置状态名称
        detailDTO.setStatusName(course.getStatus() == 1 ? "开放" : "关闭");

        // 默认不可选
        detailDTO.setSelectable(false);

        return detailDTO;
    }

    @Override
    public CourseDetailDTO getSelectableCourseDetail(Long courseId, Long studentId, Long termId) {
        // 获取课程详情
        CourseDetailDTO detailDTO = getCourseDetail(courseId);

        // 检查课程是否开放
        if (detailDTO.getStatus() != 1) {
            detailDTO.setSelectable(false);
            return detailDTO;
        }

        // 检查是否有剩余名额
        if (detailDTO.getRemainingPlaces() <= 0) {
            detailDTO.setSelectable(false);
            return detailDTO;
        }

        // 检查是否已选课程
        int selected = studentCourseMapper.checkStudentCourse(studentId, courseId, termId);
        if (selected > 0) {
            detailDTO.setSelectable(false);
            return detailDTO;
        }

        // 检查先修课是否满足
        if (detailDTO.getPrerequisiteCourses() != null && !detailDTO.getPrerequisiteCourses().isEmpty()) {
            boolean prerequisiteSatisfied = true;
            for (Course prerequisite : detailDTO.getPrerequisiteCourses()) {
                if (studentCourseMapper.checkCoursePass(studentId, prerequisite.getId()) <= 0) {
                    prerequisiteSatisfied = false;
                    break;
                }
            }
            detailDTO.setSelectable(prerequisiteSatisfied);
        } else {
            detailDTO.setSelectable(true);
        }

        return detailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCourse(Course course, List<Long> prerequisiteCourseIds) {
        // 保存课程信息
        boolean result = save(course);
        if (!result) {
            return false;
        }

        // 保存先修课关系
        if (prerequisiteCourseIds != null && !prerequisiteCourseIds.isEmpty()) {
            prerequisiteMapper.batchInsert(course.getId(), prerequisiteCourseIds);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCourse(Course course, List<Long> prerequisiteCourseIds) {
        // 更新课程信息
        boolean result = updateById(course);
        if (!result) {
            return false;
        }

        // 删除原有先修课关系
        prerequisiteMapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Prerequisite>()
                .eq(Prerequisite::getCourseId, course.getId()));

        // 保存新的先修课关系
        if (prerequisiteCourseIds != null && !prerequisiteCourseIds.isEmpty()) {
            prerequisiteMapper.batchInsert(course.getId(), prerequisiteCourseIds);
        }

        return true;
    }

    @Override
    public boolean updateCurrentStudent(Long courseId, int count) {
        return baseMapper.updateCurrentStudent(courseId, count) > 0;
    }
} 