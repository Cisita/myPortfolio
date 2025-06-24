package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级Mapper接口
 */
@Mapper
public interface ClassMapper extends BaseMapper<Class> {

    /**
     * 根据课程ID和学期ID查询班级列表
     *
     * @param courseId 课程ID
     * @param termId   学期ID
     * @return 班级列表
     */
    List<Class> getClassesByCourseAndTerm(@Param("courseId") Long courseId, @Param("termId") Long termId);

    /**
     * 根据ID查询班级详情（包含课程、教师、教室信息）
     *
     * @param id 班级ID
     * @return 班级详情
     */
    Class getClassWithDetails(@Param("id") Long id);

    /**
     * 更新班级学生数量
     *
     * @param classId 班级ID
     * @param count   增减数量
     * @return 影响行数
     */
    int updateStudentCount(@Param("classId") Long classId, @Param("count") int count);
} 