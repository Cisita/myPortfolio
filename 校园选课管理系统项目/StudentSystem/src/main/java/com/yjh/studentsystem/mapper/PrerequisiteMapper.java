package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.Prerequisite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 先修课Mapper接口
 */
@Mapper
public interface PrerequisiteMapper extends BaseMapper<Prerequisite> {

    /**
     * 根据课程ID查询先修课ID列表
     *
     * @param courseId 课程ID
     * @return 先修课ID列表
     */
    List<Long> getPrerequisiteCourseIds(@Param("courseId") Long courseId);

    /**
     * 批量插入先修课关系
     *
     * @param courseId            课程ID
     * @param prerequisiteCourseIds 先修课ID列表
     * @return 影响行数
     */
    int batchInsert(@Param("courseId") Long courseId, @Param("prerequisiteCourseIds") List<Long> prerequisiteCourseIds);
} 