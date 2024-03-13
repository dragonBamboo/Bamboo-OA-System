package com.bamboo.boa.service;

import com.bamboo.boa.domain.BoaScheduleDO;
import com.bamboo.boa.domain.vo.BoaScheduleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 日志Service接口
 * 
 * @author bamboo
 * @date 2024-03-05
 */
public interface BoaScheduleService extends IService<BoaScheduleDO>
{
    /**
     * 查询日志
     * 
     * @param id 日志主键
     * @return 日志
     */
    public BoaScheduleVO selectBoaScheduleById(Long id);

    /**
     * 查询日志列表
     *
     * @param boaSchedule 日志
     * @return 日志集合
     */
    public List<BoaScheduleVO> selectBoaScheduleList(BoaScheduleDO boaSchedule);

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
     * 批量删除日志
     * 
     * @param ids 需要删除的日志主键集合
     * @return 结果
     */
    public int deleteBoaScheduleByIds(Long[] ids);

    /**
     * 删除日志信息
     * 
     * @param id 日志主键
     * @return 结果
     */
    public int deleteBoaScheduleById(Long id);
}
