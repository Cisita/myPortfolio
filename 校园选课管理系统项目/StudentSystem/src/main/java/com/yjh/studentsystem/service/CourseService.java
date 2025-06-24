package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.dto.CourseDetailDTO;
import com.yjh.studentsystem.dto.CourseQueryDTO;
import com.yjh.studentsystem.entity.Course;

import java.util.List;

/**
 * 课程服务接口
 */
public interface CourseService extends IService<Course> {

    /**
     * 分页查询课程列表
     *
     * @param query 查询条件
     * @return 课程列表
     */
    IPage<Course> pageCourses(CourseQueryDTO query);

    /**
     * 根据ID查询课程详情
     *
     * @param id 课程ID
     * @return 课程详情
     */
    CourseDetailDTO getCourseDetail(Long id);

    /**
     * 查询学生可选的课程详情
     *
     * @param courseId  课程ID
     * @param studentId 学生ID
     * @param termId    学期ID
     * @return 课程详情
     */
    CourseDetailDTO getSelectableCourseDetail(Long courseId, Long studentId, Long termId);

    /**
     * 新增课程
     *
     * @param course              课程信息
     * @param prerequisiteCourseIds 先修课ID列表
     * @return 是否成功
     */
    boolean saveCourse(Course course, List<Long> prerequisiteCourseIds);

    /**
     * 修改课程
     *
     * @param course              课程信息
     * @param prerequisiteCourseIds 先修课ID列表
     * @return 是否成功
     */
    boolean updateCourse(Course course, List<Long> prerequisiteCourseIds);

    /**
     * 更新课程选课人数
     *
     * @param courseId 课程ID
     * @param count    增减数量
     * @return 是否成功
     */
    boolean updateCurrentStudent(Long courseId, int count);
} 