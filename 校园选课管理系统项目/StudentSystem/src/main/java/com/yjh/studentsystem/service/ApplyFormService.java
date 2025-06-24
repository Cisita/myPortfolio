package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.dto.ApplyFormDTO;
import com.yjh.studentsystem.dto.ApplyReviewDTO;
import com.yjh.studentsystem.entity.ApplyForm;
import org.springframework.web.multipart.MultipartFile;

/**
 * 补退课申请服务接口
 */
public interface ApplyFormService extends IService<ApplyForm> {

    /**
     * 提交申请
     *
     * @param applyDTO      申请信息
     * @param attachmentFiles 附件文件
     * @return 是否成功
     */
    boolean submitApply(ApplyFormDTO applyDTO, MultipartFile[] attachmentFiles);

    /**
     * 审核申请
     *
     * @param reviewDTO 审核信息
     * @return 是否成功
     */
    boolean reviewApply(ApplyReviewDTO reviewDTO);

    /**
     * 分页查询学生申请记录
     *
     * @param studentId 学生ID
     * @param termId    学期ID
     * @param type      申请类型
     * @param status    状态
     * @param pageNum   当前页码
     * @param pageSize  每页记录数
     * @return 申请记录列表
     */
    IPage<ApplyForm> pageStudentApplies(Long studentId, Long termId, Integer type, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 分页查询待审核申请记录
     *
     * @param termId   学期ID
     * @param pageNum  当前页码
     * @param pageSize 每页记录数
     * @return 申请记录列表
     */
    IPage<ApplyForm> pagePendingApplies(Long termId, Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询申请详情
     *
     * @param id 申请ID
     * @return 申请详情
     */
    ApplyForm getApplyDetail(Long id);
}