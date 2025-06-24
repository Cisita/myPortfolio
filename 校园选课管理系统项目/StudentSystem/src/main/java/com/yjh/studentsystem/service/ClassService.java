package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.dto.ClassGenerationDTO;
import com.yjh.studentsystem.entity.Class;

import java.util.List;

/**
 * 班级服务接口
 */
public interface ClassService extends IService<Class> {

    /**
     * 根据课程和学期查询班级列表
     *
     * @param courseId 课程ID
     * @param termId   学期ID
     * @return 班级列表
     */
    List<Class> getClassesByCourseAndTerm(Long courseId, Long termId);

    /**
     * 根据ID查询班级详情
     *
     * @param id 班级ID
     * @return 班级详情
     */
    Class getClassDetail(Long id);

    /**
     * 生成班级
     *
     * @param generationDTO 生成条件
     * @return 生成的班级ID列表
     */
    List<Long> generateClasses(ClassGenerationDTO generationDTO);

    /**
     * 更新班级学生数量
     *
     * @param classId 班级ID
     * @param count   增减数量
     * @return 是否成功
     */
    boolean updateStudentCount(Long classId, int count);
} 