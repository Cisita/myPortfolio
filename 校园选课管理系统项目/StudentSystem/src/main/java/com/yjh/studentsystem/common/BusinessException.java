package com.yjh.studentsystem.common;

import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public BusinessException(String message) {
        this(500, message);
    }

    /**
     * 构造函数
     *
     * @param code    状态码
     * @param message 异常信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
} 