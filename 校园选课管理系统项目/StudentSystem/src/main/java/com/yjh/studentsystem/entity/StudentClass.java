package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生班级实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student_class")
public class StudentClass extends BaseEntity {

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 学期ID
     */
    private Long termId;

    /**
     * 学生（非数据库字段）
     */
    @TableField(exist = false)
    private Student student;

    /**
     * 班级（非数据库字段）
     */
    @TableField(exist = false)
    private Class clazz;

    /**
     * 课程（非数据库字段）
     */
    @TableField(exist = false)
    private Course course;

    /**
     * 学期（非数据库字段）
     */
    @TableField(exist = false)
    private Term term;
} 