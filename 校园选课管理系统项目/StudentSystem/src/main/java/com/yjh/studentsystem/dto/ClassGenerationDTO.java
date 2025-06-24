package com.yjh.studentsystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 班级生成DTO
 */
@Data
public class ClassGenerationDTO {

    /**
     * 课程ID列表
     */
    @NotEmpty(message = "课程ID列表不能为空")
    private List<Long> courseIds;

    /**
     * 学期ID
     */
    @NotNull(message = "学期ID不能为空")
    private Long termId;

    /**
     * 班级最大容量
     */
    @NotNull(message = "班级最大容量不能为空")
    @Min(value = 1, message = "班级最大容量必须大于0")
    private Integer maxClassSize;

    /**
     * 是否按专业分组
     */
    private Boolean groupByMajor = true;
} 