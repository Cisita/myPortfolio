package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.dto.UserLoginDTO;
import com.yjh.studentsystem.dto.UserLoginResponseDTO;
import com.yjh.studentsystem.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    UserLoginResponseDTO login(UserLoginDTO loginDTO);

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    User getCurrentUser();
} 