package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 课程实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("course")
public class Course extends BaseEntity {

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
     * 课程简介
     */
    private String introduction;

    /**
     * 开课院系
     */
    private String department;

    /**
     * 授课教师ID
     */
    private Long teacherId;

    /**
     * 最大学生数
     */
    private Integer maxStudent;

    /**
     * 当前选课人数
     */
    private Integer currentStudent;

    /**
     * 状态：0-关闭，1-开放
     */
    private Integer status;

    /**
     * 授课教师（非数据库字段）
     */
    @TableField(exist = false)
    private Teacher teacher;

    /**
     * 先修课程列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Course> prerequisiteCourses;

    /**
     * 课程时间表（非数据库字段）
     */
    @TableField(exist = false)
    private List<CourseSchedule> schedules;
} 