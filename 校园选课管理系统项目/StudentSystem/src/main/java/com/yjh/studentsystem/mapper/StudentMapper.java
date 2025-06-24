package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 根据ID查询学生信息（包含用户信息）
     *
     * @param id 学生ID
     * @return 学生信息
     */
    Student getStudentWithUser(@Param("id") Long id);
} 