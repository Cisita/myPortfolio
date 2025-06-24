package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.CourseSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程时间表Mapper接口
 */
@Mapper
public interface CourseScheduleMapper extends BaseMapper<CourseSchedule> {

    /**
     * 根据课程ID和学期ID查询课程时间表
     *
     * @param courseId 课程ID
     * @param termId   学期ID
     * @return 课程时间表列表
     */
    List<CourseSchedule> getSchedulesByCourseAndTerm(@Param("courseId") Long courseId, @Param("termId") Long termId);

    /**
     * 查询学生时间冲突的课程ID列表
     *
     * @param studentId 学生ID
     * @param termId    学期ID
     * @param dayOfWeek 星期几
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param weekRange 周次范围
     * @return 课程ID列表
     */
    List<Long> getTimeConflictCourses(@Param("studentId") Long studentId,
                                      @Param("termId") Long termId,
                                      @Param("dayOfWeek") Integer dayOfWeek,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime,
                                      @Param("weekRange") String weekRange);
} 