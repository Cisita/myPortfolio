package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.ApplyAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请附件Mapper接口
 */
@Mapper
public interface ApplyAttachmentMapper extends BaseMapper<ApplyAttachment> {

    /**
     * 根据申请ID查询附件列表
     *
     * @param applyId 申请ID
     * @return 附件列表
     */
    List<ApplyAttachment> getAttachmentsByApplyId(@Param("applyId") Long applyId);

    /**
     * 批量插入附件
     *
     * @param attachments 附件列表
     * @return 影响行数
     */
    int batchInsert(@Param("attachments") List<ApplyAttachment> attachments);
} 