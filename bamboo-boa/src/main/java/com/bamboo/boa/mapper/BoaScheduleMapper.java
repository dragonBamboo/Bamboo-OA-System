package com.bamboo.boa.mapper;

import java.util.List;
import com.bamboo.boa.domain.BoaScheduleDO;
import com.bamboo.common.utils.sql.BaseMapperX;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 日志Mapper接口
 * 
 * @author bamboo
 * @date 2024-03-05
 */
@Repository
public interface BoaScheduleMapper extends BaseMapperX<BoaScheduleDO>
{
    /**
     * 查询日志
     * 
     * @param id 日志主键
     * @return 日志
     */
    public BoaScheduleDO selectBoaScheduleById(Long id);

    /**
     * 查询日志列表
     * 
     * @param boaSchedule 日志
     * @return 日志集合
     */
    public List<BoaScheduleDO> selectBoaScheduleList(BoaScheduleDO boaSchedule);

    /**
     * 新增日志
     * 
     * @param boaSchedule 日志
     * @return 结果
     */
    public int insertBoaSchedule(BoaScheduleDO boaSchedule);

    /**
     * 修改日志
     * 
     * @param boaSchedule 日志
     * @return 结果
     */
    public int updateBoaSchedule(BoaScheduleDO boaSchedule);

    /**
     * 删除日志
     * 
     * @param id 日志主键
     * @return 结果
     */
    public int deleteBoaScheduleById(Long id);

    /**
     * 批量删除日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBoaScheduleByIds(Long[] ids);
}
