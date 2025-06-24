package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 先修课实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("prerequisite")
public class Prerequisite extends BaseEntity {

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 先修课程ID
     */
    private Long prerequisiteCourseId;

    /**
     * 课程（非数据库字段）
     */
    @TableField(exist = false)
    private Course course;

    /**
     * 先修课程（非数据库字段）
     */
    @TableField(exist = false)
    private Course prerequisiteCourse;
} 