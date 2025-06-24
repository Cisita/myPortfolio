package com.yjh.studentsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjh.studentsystem.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教室Mapper接口
 */
@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {

    /**
     * 查询可用教室列表
     *
     * @param minCapacity 最小容量
     * @return 教室列表
     */
    List<Classroom> getAvailableClassrooms(@Param("minCapacity") Integer minCapacity);

    /**
     * 查询时间冲突的教室ID列表
     *
     * @param termId     学期ID
     * @param dayOfWeek  星期几
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param weekRange  周次范围
     * @return 教室ID列表
     */
    List<Long> getConflictClassrooms(@Param("termId") Long termId,
                                    @Param("dayOfWeek") Integer dayOfWeek,
                                    @Param("startTime") String startTime,
                                    @Param("endTime") String endTime,
                                    @Param("weekRange") String weekRange);
} 