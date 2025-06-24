package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.common.Constants;
import com.yjh.studentsystem.dto.ApplyFormDTO;
import com.yjh.studentsystem.dto.ApplyReviewDTO;
import com.yjh.studentsystem.dto.CourseSelectionDTO;
import com.yjh.studentsystem.entity.ApplyAttachment;
import com.yjh.studentsystem.entity.ApplyForm;
import com.yjh.studentsystem.mapper.ApplyAttachmentMapper;
import com.yjh.studentsystem.mapper.ApplyFormMapper;
import com.yjh.studentsystem.service.ApplyFormService;
import com.yjh.studentsystem.service.CourseSelectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 补退课申请服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyFormServiceImpl extends ServiceImpl<ApplyFormMapper, ApplyForm> implements ApplyFormService {

    private final ApplyAttachmentMapper applyAttachmentMapper;
    private final CourseSelectionService courseSelectionService;

    @Value("${file.upload-path:/uploads}")
    private String uploadPath;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitApply(ApplyFormDTO applyDTO, MultipartFile[] attachmentFiles) {
        // 创建申请记录
        ApplyForm applyForm = new ApplyForm();
        applyForm.setStudentId(applyDTO.getStudentId());
        applyForm.setCourseId(applyDTO.getCourseId());
        applyForm.setTermId(applyDTO.getTermId());
        applyForm.setType(applyDTO.getType());
        applyForm.setReason(applyDTO.getReason());
        applyForm.setStatus(Constants.APPLY_STATUS_PENDING);

        // 保存申请记录
        boolean result = save(applyForm);
        if (!result) {
            throw new BusinessException("提交申请失败");
        }

        // 上传附件并保存附件记录
        if (attachmentFiles != null && attachmentFiles.length > 0) {
            List<ApplyAttachment> attachments = uploadAttachments(applyForm.getId(), attachmentFiles);
            if (!attachments.isEmpty()) {
                applyAttachmentMapper.batchInsert(attachments);
            }
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewApply(ApplyReviewDTO reviewDTO) {
        // 查询申请记录
        ApplyForm applyForm = getById(reviewDTO.getApplyId());
        if (applyForm == null) {
            throw new BusinessException("申请记录不存在");
        }

        // 检查申请状态
        if (applyForm.getStatus() != Constants.APPLY_STATUS_PENDING) {
            throw new BusinessException("申请已审核，请勿重复操作");
        }

        // 更新申请状态
        applyForm.setStatus(reviewDTO.getStatus());
        applyForm.setReviewerId(reviewDTO.getReviewerId());
        applyForm.setReviewTime(LocalDateTime.now());
        applyForm.setReviewReason(reviewDTO.getReviewReason());
        updateById(applyForm);

        // 如果审核通过，执行补选或退课操作
        if (reviewDTO.getStatus() == Constants.APPLY_STATUS_APPROVED) {
            if (applyForm.getType() == Constants.APPLY_TYPE_ADD) {
                // 执行补选操作
                CourseSelectionDTO selectionDTO = new CourseSelectionDTO();
                selectionDTO.setStudentId(applyForm.getStudentId());
                selectionDTO.setCourseId(applyForm.getCourseId());
                selectionDTO.setTermId(applyForm.getTermId());
                courseSelectionService.selectCourse(selectionDTO);
            } else if (applyForm.getType() == Constants.APPLY_TYPE_WITHDRAW) {
                // 查询选课记录ID
                com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.yjh.studentsystem.entity.StudentCourse> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
                wrapper.eq(com.yjh.studentsystem.entity.StudentCourse::getStudentId, applyForm.getStudentId())
                        .eq(com.yjh.studentsystem.entity.StudentCourse::getCourseId, applyForm.getCourseId())
                        .eq(com.yjh.studentsystem.entity.StudentCourse::getTermId, applyForm.getTermId())
                        .eq(com.yjh.studentsystem.entity.StudentCourse::getStatus, Constants.SELECTION_STATUS_SELECTED);
                com.yjh.studentsystem.entity.StudentCourse studentCourse = courseSelectionService.getOne(wrapper);
                if (studentCourse != null) {
                    // 执行退课操作
                    courseSelectionService.withdrawCourse(studentCourse.getId());
                }
            }
        }

        return true;
    }

    @Override
    public IPage<ApplyForm> pageStudentApplies(Long studentId, Long termId, Integer type, Integer status, Integer pageNum, Integer pageSize) {
        IPage<ApplyForm> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectStudentApplyPage(page, studentId, termId, type, status);
    }

    @Override
    public IPage<ApplyForm> pagePendingApplies(Long termId, Integer pageNum, Integer pageSize) {
        IPage<ApplyForm> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPendingApplyPage(page, termId);
    }

    @Override
    public ApplyForm getApplyDetail(Long id) {
        return baseMapper.getApplyWithDetails(id);
    }

    /**
     * 上传附件并保存附件记录
     *
     * @param applyId   申请ID
     * @param files     附件文件
     * @return 附件记录列表
     */
    private List<ApplyAttachment> uploadAttachments(Long applyId, MultipartFile[] files) {
        List<ApplyAttachment> attachments = new ArrayList<>();

        // 检查上传目录是否存在，不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            try {
                // 生成文件名
                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String filename = UUID.randomUUID().toString().replace("-", "") + suffix;

                // 上传文件
                File destFile = new File(uploadPath + File.separator + filename);
                file.transferTo(destFile);

                // 创建附件记录
                ApplyAttachment attachment = new ApplyAttachment();
                attachment.setApplyId(applyId);
                attachment.setFileName(originalFilename);
                attachment.setFilePath(uploadPath + File.separator + filename);
                attachment.setFileSize(file.getSize());
                attachments.add(attachment);
            } catch (IOException e) {
                log.error("文件上传失败：{}", e.getMessage());
                throw new BusinessException("文件上传失败");
            }
        }

        return attachments;
    }
} 