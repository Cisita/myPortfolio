package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别：0-女，1-男
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户类型：0-学生，1-教师，2-管理员
     */
    private Integer userType;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
} 