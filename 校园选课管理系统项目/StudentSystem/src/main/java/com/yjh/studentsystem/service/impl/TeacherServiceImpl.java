package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.common.Constants;
import com.yjh.studentsystem.entity.Teacher;
import com.yjh.studentsystem.entity.User;
import com.yjh.studentsystem.mapper.TeacherMapper;
import com.yjh.studentsystem.service.TeacherService;
import com.yjh.studentsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教师服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    private final UserService userService;

    @Override
    public Teacher getTeacherWithUser(Long id) {
        return baseMapper.getTeacherWithUser(id);
    }

    @Override
    public Teacher getByUserId(Long userId) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getId, userId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTeacher(Teacher teacher) {
        // 校验工号是否已存在
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getTeacherNo, teacher.getTeacherNo());
        if (count(wrapper) > 0) {
            throw new BusinessException("工号已存在");
        }

        // 保存用户信息
        User user = teacher.getUser();
        if (user == null) {
            throw new BusinessException("用户信息不能为空");
        }

        user.setUserType(Constants.USER_TYPE_TEACHER);
        boolean userResult = userService.save(user);
        if (!userResult) {
            return false;
        }

        // 保存教师信息
        teacher.setId(user.getId());
        return save(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeacher(Teacher teacher) {
        // 校验工号是否已存在
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getTeacherNo, teacher.getTeacherNo())
                .ne(Teacher::getId, teacher.getId());
        if (count(wrapper) > 0) {
            throw new BusinessException("工号已存在");
        }

        // 更新用户信息
        User user = teacher.getUser();
        if (user != null) {
            user.setId(teacher.getId());
            boolean userResult = userService.updateById(user);
            if (!userResult) {
                return false;
            }
        }

        // 更新教师信息
        return updateById(teacher);
    }
} 