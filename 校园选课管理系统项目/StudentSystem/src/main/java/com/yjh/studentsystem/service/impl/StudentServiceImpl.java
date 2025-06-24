package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.common.Constants;
import com.yjh.studentsystem.entity.Student;
import com.yjh.studentsystem.entity.User;
import com.yjh.studentsystem.mapper.StudentMapper;
import com.yjh.studentsystem.service.StudentService;
import com.yjh.studentsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final UserService userService;

    @Override
    public Student getStudentWithUser(Long id) {
        return baseMapper.getStudentWithUser(id);
    }

    @Override
    public Student getByUserId(Long userId) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId, userId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveStudent(Student student) {
        // 校验学号是否已存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentNo, student.getStudentNo());
        if (count(wrapper) > 0) {
            throw new BusinessException("学号已存在");
        }

        // 保存用户信息
        User user = student.getUser();
        if (user == null) {
            throw new BusinessException("用户信息不能为空");
        }

        user.setUserType(Constants.USER_TYPE_STUDENT);
        boolean userResult = userService.save(user);
        if (!userResult) {
            return false;
        }

        // 保存学生信息
        student.setId(user.getId());
        return save(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudent(Student student) {
        // 校验学号是否已存在
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentNo, student.getStudentNo())
                .ne(Student::getId, student.getId());
        if (count(wrapper) > 0) {
            throw new BusinessException("学号已存在");
        }

        // 更新用户信息
        User user = student.getUser();
        if (user != null) {
            user.setId(student.getId());
            boolean userResult = userService.updateById(user);
            if (!userResult) {
                return false;
            }
        }

        // 更新学生信息
        return updateById(student);
    }
} 