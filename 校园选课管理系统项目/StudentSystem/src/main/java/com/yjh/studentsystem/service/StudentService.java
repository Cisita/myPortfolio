package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.entity.Student;

/**
 * 学生服务接口
 */
public interface StudentService extends IService<Student> {

    /**
     * 根据ID查询学生信息（包含用户信息）
     *
     * @param id 学生ID
     * @return 学生信息
     */
    Student getStudentWithUser(Long id);

    /**
     * 根据用户ID查询学生信息
     *
     * @param userId 用户ID
     * @return 学生信息
     */
    Student getByUserId(Long userId);

    /**
     * 新增学生
     *
     * @param student 学生信息
     * @return 是否成功
     */
    boolean saveStudent(Student student);

    /**
     * 修改学生信息
     *
     * @param student 学生信息
     * @return 是否成功
     */
    boolean updateStudent(Student student);
} 