package com.yjh.studentsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjh.studentsystem.entity.Term;

/**
 * 学期服务接口
 */
public interface TermService extends IService<Term> {

    /**
     * 获取当前激活的学期
     *
     * @return 学期信息
     */
    Term getCurrentTerm();

    /**
     * 检查当前是否在选课时间内
     *
     * @param termId 学期ID
     * @return 是否在选课时间内
     */
    boolean isSelectionTime(Long termId);

    /**
     * 检查当前是否在退课时间内
     *
     * @param termId 学期ID
     * @return 是否在退课时间内
     */
    boolean isWithdrawTime(Long termId);

    /**
     * 激活学期
     *
     * @param termId 学期ID
     * @return 是否成功
     */
    boolean activateTerm(Long termId);
} 