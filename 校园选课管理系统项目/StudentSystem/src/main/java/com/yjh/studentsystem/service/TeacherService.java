package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.entity.Teacher;

/**
 * 教师服务接口
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 根据ID查询教师信息（包含用户信息）
     *
     * @param id 教师ID
     * @return 教师信息
     */
    Teacher getTeacherWithUser(Long id);

    /**
     * 根据用户ID查询教师信息
     *
     * @param userId 用户ID
     * @return 教师信息
     */
    Teacher getByUserId(Long userId);

    /**
     * 新增教师
     *
     * @param teacher 教师信息
     * @return 是否成功
     */
    boolean saveTeacher(Teacher teacher);

    /**
     * 修改教师信息
     *
     * @param teacher 教师信息
     * @return 是否成功
     */
    boolean updateTeacher(Teacher teacher);
} 