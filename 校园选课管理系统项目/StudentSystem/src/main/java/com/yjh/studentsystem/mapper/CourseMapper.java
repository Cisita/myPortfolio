package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjh.studentsystem.dto.CourseQueryDTO;
import com.yjh.studentsystem.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 分页查询课程列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 课程列表
     */
    IPage<Course> selectCoursePage(IPage<Course> page, @Param("query") CourseQueryDTO query);

    /**
     * 根据ID查询课程详情（包含教师信息）
     *
     * @param id 课程ID
     * @return 课程详情
     */
    Course getCourseWithTeacher(@Param("id") Long id);

    /**
     * 根据ID查询先修课程列表
     *
     * @param courseId 课程ID
     * @return 先修课程列表
     */
    List<Course> getPrerequisiteCourses(@Param("courseId") Long courseId);

    /**
     * 更新课程选课人数
     *
     * @param courseId 课程ID
     * @param count    增减数量
     * @return 影响行数
     */
    int updateCurrentStudent(@Param("courseId") Long courseId, @Param("count") int count);
} 