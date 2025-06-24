package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 教师实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("teacher")
public class Teacher {

    /**
     * 教师ID，关联用户表ID
     */
    @TableId
    private Long id;

    /**
     * 工号
     */
    private String teacherNo;

    /**
     * 职称
     */
    private String title;

    /**
     * 院系
     */
    private String department;

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