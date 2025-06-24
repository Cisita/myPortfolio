package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjh.studentsystem.entity.ApplyForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 补退课申请Mapper接口
 */
@Mapper
public interface ApplyFormMapper extends BaseMapper<ApplyForm> {

    /**
     * 分页查询学生申请记录
     *
     * @param page      分页参数
     * @param studentId 学生ID
     * @param termId    学期ID
     * @param type      申请类型
     * @param status    状态
     * @return 申请记录列表
     */
    IPage<ApplyForm> selectStudentApplyPage(IPage<ApplyForm> page,
                                           @Param("studentId") Long studentId,
                                           @Param("termId") Long termId,
                                           @Param("type") Integer type,
                                           @Param("status") Integer status);

    /**
     * 分页查询待审核申请记录
     *
     * @param page   分页参数
     * @param termId 学期ID
     * @return 申请记录列表
     */
    IPage<ApplyForm> selectPendingApplyPage(IPage<ApplyForm> page, @Param("termId") Long termId);

    /**
     * 根据ID查询申请详情（包含学生、课程、附件信息）
     *
     * @param id 申请ID
     * @return 申请详情
     */
    ApplyForm getApplyWithDetails(@Param("id") Long id);
}