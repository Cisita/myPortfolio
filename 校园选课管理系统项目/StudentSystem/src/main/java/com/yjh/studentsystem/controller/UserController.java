package com.yjh.studentsystem.controller;

import com.yjh.studentsystem.common.Result;
import com.yjh.studentsystem.dto.UserLoginDTO;
import com.yjh.studentsystem.dto.UserLoginResponseDTO;
import com.yjh.studentsystem.entity.User;
import com.yjh.studentsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginDTO loginDTO) {
        UserLoginResponseDTO responseDTO = userService.login(loginDTO);
        return Result.success(responseDTO);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/current")
    public Result<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return Result.success(user);
    }
} 