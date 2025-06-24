package com.yjh.studentsystem.dto;

import lombok.Data;

/**
 * 用户登录响应DTO
 */
@Data
public class UserLoginResponseDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户类型：0-学生，1-教师，2-管理员
     */
    private Integer userType;

    /**
     * token
     */
    private String token;
} 