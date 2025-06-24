package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 补退课申请实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("apply_form")
public class ApplyForm extends BaseEntity {

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 学期ID
     */
    private Long termId;

    /**
     * 申请类型：0-补选，1-退课
     */
    private Integer type;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 状态：0-待审核，1-通过，2-驳回
     */
    private Integer status;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    private String reviewReason;

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
     * 学期（非数据库字段）
     */
    @TableField(exist = false)
    private Term term;

    /**
     * 审核人（非数据库字段）
     */
    @TableField(exist = false)
    private User reviewer;

    /**
     * 附件列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<ApplyAttachment> attachments;
} 