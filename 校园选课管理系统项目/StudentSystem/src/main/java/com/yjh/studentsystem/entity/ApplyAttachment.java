package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 申请附件实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("apply_attachment")
public class ApplyAttachment extends BaseEntity {

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * 申请表单（非数据库字段）
     */
    @TableField(exist = false)
    private ApplyForm applyForm;
} 