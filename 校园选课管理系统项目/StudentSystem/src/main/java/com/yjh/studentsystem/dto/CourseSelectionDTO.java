package com.yjh.studentsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 学生选课DTO
 */
@Data
public class CourseSelectionDTO {

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
} 