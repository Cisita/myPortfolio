package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学生选课实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student_course")
public class StudentCourse extends BaseEntity {

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 学期ID
     */
    private Long termId;

    /**
     * 状态：0-待提交，1-已选，2-退课
     */
    private Integer status;

    /**
     * 成绩
     */
    private BigDecimal score;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 学生（非数据库字段）
     */
    @TableField(exist = false)
    private Student student;

    /**
     * 课程（非数据库字段）
     */
    @TableField(exist = false)
    private Course course;

    /**
     * 班级（非数据库字段）
     */
    @TableField(exist = false)
    private Class clazz;

    /**
     * 学期（非数据库字段）
     */
    @TableField(exist = false)
    private Term term;
} 