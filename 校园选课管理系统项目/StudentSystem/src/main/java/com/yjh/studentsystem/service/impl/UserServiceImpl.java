package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.common.Constants;
import com.yjh.studentsystem.dto.UserLoginDTO;
import com.yjh.studentsystem.dto.UserLoginResponseDTO;
import com.yjh.studentsystem.entity.User;
import com.yjh.studentsystem.mapper.UserMapper;
import com.yjh.studentsystem.service.UserService;
import com.yjh.studentsystem.util.JwtUtil;
import com.yjh.studentsystem.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtUtil jwtUtil;

    @Override
    public UserLoginResponseDTO login(UserLoginDTO loginDTO) {
        // 查询用户
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, loginDTO.getUsername()));

        // 校验用户是否存在
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 校验密码
        if (!PasswordUtil.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 校验用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("用户已禁用");
        }

        // 生成token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getUserType());

        // 构建返回结果
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setUserId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setName(user.getName());
        responseDTO.setUserType(user.getUserType());
        responseDTO.setToken(token);

        return responseDTO;
    }

    @Override
    public User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith(Constants.TOKEN_PREFIX)) {
            throw new BusinessException(401, "未登录");
        }
        
        token = token.substring(Constants.TOKEN_PREFIX.length());
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException(401, "登录已过期");
        }
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        return getById(userId);
    }
} 