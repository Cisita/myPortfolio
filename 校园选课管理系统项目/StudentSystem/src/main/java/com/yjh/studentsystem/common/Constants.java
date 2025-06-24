package com.yjh.studentsystem.common;

/**
 * 常量类
 */
public class Constants {

    /**
     * 用户类型：学生
     */
    public static final int USER_TYPE_STUDENT = 0;

    /**
     * 用户类型：教师
     */
    public static final int USER_TYPE_TEACHER = 1;

    /**
     * 用户类型：管理员
     */
    public static final int USER_TYPE_ADMIN = 2;

    /**
     * 课程类型：必修
     */
    public static final int COURSE_TYPE_REQUIRED = 0;

    /**
     * 课程类型：选修
     */
    public static final int COURSE_TYPE_ELECTIVE = 1;

    /**
     * 选课状态：待提交
     */
    public static final int SELECTION_STATUS_PENDING = 0;

    /**
     * 选课状态：已选
     */
    public static final int SELECTION_STATUS_SELECTED = 1;

    /**
     * 选课状态：退课
     */
    public static final int SELECTION_STATUS_WITHDRAWN = 2;

    /**
     * 申请类型：补选
     */
    public static final int APPLY_TYPE_ADD = 0;

    /**
     * 申请类型：退课
     */
    public static final int APPLY_TYPE_WITHDRAW = 1;

    /**
     * 申请状态：待审核
     */
    public static final int APPLY_STATUS_PENDING = 0;

    /**
     * 申请状态：通过
     */
    public static final int APPLY_STATUS_APPROVED = 1;

    /**
     * 申请状态：驳回
     */
    public static final int APPLY_STATUS_REJECTED = 2;

    /**
     * 教室类型：普通教室
     */
    public static final int CLASSROOM_TYPE_NORMAL = 0;

    /**
     * 教室类型：实验室
     */
    public static final int CLASSROOM_TYPE_LAB = 1;

    /**
     * 教室类型：多媒体教室
     */
    public static final int CLASSROOM_TYPE_MULTIMEDIA = 2;

    /**
     * Token过期时间（24小时）
     */
    public static final long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * JWT声明：用户ID
     */
    public static final String CLAIM_USER_ID = "userId";

    /**
     * JWT声明：用户名
     */
    public static final String CLAIM_USERNAME = "username";

    /**
     * JWT声明：用户类型
     */
    public static final String CLAIM_USER_TYPE = "userType";
} 