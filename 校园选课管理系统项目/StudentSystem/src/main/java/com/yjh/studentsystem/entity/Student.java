package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("student")
public class Student {

    /**
     * 学生ID，关联用户表ID
     */
    @TableId
    private Long id;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String className;

    /**
     * 年级
     */
    private String grade;

    /**
     * 入学日期
     */
    private LocalDate admissionDate;

    /**
     * 逻辑删除字段：0-未删除，1-已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 关联的用户信息（非数据库字段）
     */
    @TableField(exist = false)
    private User user;
} 