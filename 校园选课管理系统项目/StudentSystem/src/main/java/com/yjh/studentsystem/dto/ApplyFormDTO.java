package com.yjh.studentsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 补退课申请DTO
 */
@Data
public class ApplyFormDTO {

    /**
     * 学生ID
     */
    @NotNull(message = "学生ID不能为空")
    private Long studentId;

    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    /**
     * 学期ID
     */
    @NotNull(message = "学期ID不能为空")
    private Long termId;

    /**
     * 申请类型：0-补选，1-退课
     */
    @NotNull(message = "申请类型不能为空")
    private Integer type;

    /**
     * 申请理由
     */
    @NotBlank(message = "申请理由不能为空")
    private String reason;
} 