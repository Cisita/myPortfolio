package com.yjh.studentsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程查询DTO
 */
@Data
public class CourseQueryDTO {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程代码
     */
    private String courseCode;

    /**
     * 课程类型：0-必修，1-选修
     */
    private Integer courseType;

    /**
     * 开课院系
     */
    private String department;

    /**
     * 授课教师姓名
     */
    private String teacherName;

    /**
     * 学分下限
     */
    private BigDecimal creditMin;

    /**
     * 学分上限
     */
    private BigDecimal creditMax;

    /**
     * 星期几：1-7表示周一至周日
     */
    private Integer dayOfWeek;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;
} 