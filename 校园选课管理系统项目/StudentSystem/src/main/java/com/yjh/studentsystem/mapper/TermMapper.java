package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学期Mapper接口
 */
@Mapper
public interface TermMapper extends BaseMapper<Term> {

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
     * @return 选课时间内返回1，否则返回0
     */
    int checkSelectionTime(@Param("termId") Long termId);

    /**
     * 检查当前是否在退课时间内
     *
     * @param termId 学期ID
     * @return 退课时间内返回1，否则返回0
     */
    int checkWithdrawTime(@Param("termId") Long termId);
}