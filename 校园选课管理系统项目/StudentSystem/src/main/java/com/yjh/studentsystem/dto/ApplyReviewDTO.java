package com.yjh.studentsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 补退课审核DTO
 */
@Data
public class ApplyReviewDTO {

    /**
     * 申请ID
     */
    @NotNull(message = "申请ID不能为空")
    private Long applyId;

    /**
     * 审核人ID
     */
    @NotNull(message = "审核人ID不能为空")
    private Long reviewerId;

    /**
     * 审核状态：1-通过，2-驳回
     */
    @NotNull(message = "审核状态不能为空")
    private Integer status;

    /**
     * 审核意见
     */
    private String reviewReason;
} 