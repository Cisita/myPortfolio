package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 班级实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("class")
public class Class extends BaseEntity {

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 教师ID
     */
    private Long teacherId;

    /**
     * 教室ID
     */
    private Long classroomId;

    /**
     * 学期ID
     */
    private Long termId;

    /**
     * 学生数量
     */
    private Integer studentCount;

    /**
     * 课程（非数据库字段）
     */
    @TableField(exist = false)
    private Course course;

    /**
     * 教师（非数据库字段）
     */
    @TableField(exist = false)
    private Teacher teacher;

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

    /**
     * 学生列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Student> students;
} 