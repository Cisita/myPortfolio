package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 教师Mapper接口
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 根据ID查询教师信息（包含用户信息）
     *
     * @param id 教师ID
     * @return 教师信息
     */
    Teacher getTeacherWithUser(@Param("id") Long id);
} 