package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.StudentClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生班级Mapper接口
 */
@Mapper
public interface StudentClassMapper extends BaseMapper<StudentClass> {

    /**
     * 根据班级ID查询学生列表
     *
     * @param classId 班级ID
     * @return 学生班级关联列表
     */
    List<StudentClass> getStudentsByClassId(@Param("classId") Long classId);

    /**
     * 批量插入学生班级关联
     *
     * @param studentClasses 学生班级关联列表
     * @return 影响行数
     */
    int batchInsert(@Param("studentClasses") List<StudentClass> studentClasses);
} 