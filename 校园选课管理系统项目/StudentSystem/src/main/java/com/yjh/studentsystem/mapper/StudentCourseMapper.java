package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjh.studentsystem.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生选课Mapper接口
 */
@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    /**
     * 分页查询学生选课记录（包含课程、班级信息）
     *
     * @param page      分页参数
     * @param studentId 学生ID
     * @param termId    学期ID
     * @param status    状态
     * @return 选课记录列表
     */
    IPage<StudentCourse> selectStudentCoursePage(IPage<StudentCourse> page,
                                                @Param("studentId") Long studentId,
                                                @Param("termId") Long termId,
                                                @Param("status") Integer status);

    /**
     * 根据ID查询学生选课记录（包含课程、班级、学期信息）
     *
     * @param id 选课记录ID
     * @return 选课记录
     */
    StudentCourse getStudentCourseWithDetails(@Param("id") Long id);

    /**
     * 检查学生是否已选某课程
     *
     * @param studentId 学生ID
     * @param courseId  课程ID
     * @param termId    学期ID
     * @return 已选返回1，未选返回0
     */
    int checkStudentCourse(@Param("studentId") Long studentId,
                          @Param("courseId") Long courseId,
                          @Param("termId") Long termId);

    /**
     * 检查学生是否通过某课程
     *
     * @param studentId 学生ID
     * @param courseId  课程ID
     * @return 通过返回1，未通过返回0
     */
    int checkCoursePass(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    /**
     * 根据课程和学期获取已选学生ID列表
     *
     * @param courseId 课程ID
     * @param termId   学期ID
     * @param status   状态
     * @return 学生ID列表
     */
    List<Long> getSelectedStudentIds(@Param("courseId") Long courseId,
                                    @Param("termId") Long termId,
                                    @Param("status") Integer status);
} 