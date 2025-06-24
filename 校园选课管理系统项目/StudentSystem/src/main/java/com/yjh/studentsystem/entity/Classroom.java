package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教室实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("classroom")
public class Classroom extends BaseEntity {

    /**
     * 教室名称
     */
    private String classroomName;

    /**
     * 教学楼
     */
    private String building;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 教室类型：0-普通教室，1-实验室，2-多媒体教室
     */
    private Integer classroomType;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
} 