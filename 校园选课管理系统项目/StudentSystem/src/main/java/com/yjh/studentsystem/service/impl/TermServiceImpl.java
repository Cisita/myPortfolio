package com.yjh.studentsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.studentsystem.common.BusinessException;
import com.yjh.studentsystem.entity.Term;
import com.yjh.studentsystem.mapper.TermMapper;
import com.yjh.studentsystem.service.TermService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学期服务实现类
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TermServiceImpl extends ServiceImpl<TermMapper, Term> implements TermService {

    @Override
    public Term getCurrentTerm() {
        Term term = baseMapper.getCurrentTerm();
        if (term == null) {
            throw new BusinessException("当前没有激活的学期");
        }
        return term;
    }

    @Override
    public boolean isSelectionTime(Long termId) {
        return baseMapper.checkSelectionTime(termId) > 0;
    }

    @Override
    public boolean isWithdrawTime(Long termId) {
        return baseMapper.checkWithdrawTime(termId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean activateTerm(Long termId) {
        // 查询学期是否存在
        Term term = getById(termId);
        if (term == null) {
            throw new BusinessException("学期不存在");
        }

        // 将所有学期状态设为未激活
        LambdaUpdateWrapper<Term> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Term::getStatus, 0);
        update(wrapper);

        // 激活当前学期
        term.setStatus(1);
        return updateById(term);
    }
} 