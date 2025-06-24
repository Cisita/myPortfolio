package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.dto.CourseSelectionDTO;
import com.yjh.studentsystem.entity.StudentCourse;

/**
 * 课程选择服务接口
 */
public interface CourseSelectionService extends IService<StudentCourse> {

    /**
     * 选课
     *
     * @param selectionDTO 选课信息
     * @return 是否成功
     */
    boolean selectCourse(CourseSelectionDTO selectionDTO);

    /**
     * 退课
     *
     * @param selectionId 选课记录ID
     * @return 是否成功
     */
    boolean withdrawCourse(Long selectionId);

    /**
     * 分页查询学生选课记录
     *
     * @param studentId 学生ID
     * @param termId    学期ID
     * @param status    状态
     * @param pageNum   当前页码
     * @param pageSize  每页记录数
     * @return 选课记录列表
     */
    IPage<StudentCourse> pageStudentCourses(Long studentId, Long termId, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询学生选课记录
     *
     * @param id 选课记录ID
     * @return 选课记录
     */
    StudentCourse getStudentCourseDetail(Long id);

    /**
     * 检查先修课是否满足
     *
     * @param studentId 学生ID
     * @param courseId  课程ID
     * @return 是否满足
     */
    boolean checkPrerequisite(Long studentId, Long courseId);

    /**
     * 检查课程是否有剩余名额
     *
     * @param courseId 课程ID
     * @return 是否有剩余名额
     */
    boolean checkCourseAvailable(Long courseId);

    /**
     * 检查课程时间是否冲突
     *
     * @param studentId 学生ID
     * @param courseId  课程ID
     * @param termId    学期ID
     * @return 是否冲突
     */
    boolean checkTimeConflict(Long studentId, Long courseId, Long termId);
} 