package com.yjh.studentsystem.dto;

import com.yjh.studentsystem.entity.Course;
import com.yjh.studentsystem.entity.CourseSchedule;
import com.yjh.studentsystem.entity.Teacher;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程详情DTO
 */
@Data
public class CourseDetailDTO {

    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 学分
     */
    private BigDecimal credit;

    /**
     * 课时
     */
    private Integer period;

    /**
     * 课程类型：0-必修，1-选修
     */
    private Integer courseType;

    /**
     * 课程类型名称
     */
    private String courseTypeName;

    /**
     * 课程简介
     */
    private String introduction;

    /**
     * 开课院系
     */
    private String department;

    /**
     * 最大学生数
     */
    private Integer maxStudent;

    /**
     * 当前选课人数
     */
    private Integer currentStudent;

    /**
     * 剩余名额
     */
    private Integer remainingPlaces;

    /**
     * 状态：0-关闭，1-开放
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 授课教师
     */
    private Teacher teacher;

    /**
     * 先修课程列表
     */
    private List<Course> prerequisiteCourses;

    /**
     * 课程时间表
     */
    private List<CourseSchedule> schedules;

    /**
     * 是否可选
     */
    private Boolean selectable;
} 