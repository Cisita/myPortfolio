package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

/**
 * 课程时间表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("course_schedule")
public class CourseSchedule extends BaseEntity {

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 教室ID
     */
    private Long classroomId;

    /**
     * 学期ID
     */
    private Long termId;

    /**
     * 星期几：1-7表示周一至周日
     */
    private Integer dayOfWeek;

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 结束时间
     */
    private LocalTime endTime;

    /**
     * 周次范围，如1-16
     */
    private String weekRange;

    /**
     * 课程（非数据库字段）
     */
    @TableField(exist = false)
    private Course course;

    /**
     * 教室（非数据库字段）
     */
    @TableField(exist = false)
    private Classroom classroom;

    /**
     * 学期（非数据库字段）
     */
    @TableField(exist = false)
    private Term term;
} 