package com.yjh.studentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yjh.studentsystem.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学期实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("term")
public class Term extends BaseEntity {

    /**
     * 学期名称
     */
    private String termName;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 选课开始时间
     */
    private LocalDateTime selectionStartTime;

    /**
     * 选课结束时间
     */
    private LocalDateTime selectionEndTime;

    /**
     * 退课截止时间
     */
    private LocalDateTime withdrawEndTime;

    /**
     * 状态：0-未激活，1-激活
     */
    private Integer status;
} 