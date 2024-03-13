package com.bamboo.boa.mapper;

import java.util.List;
import com.bamboo.boa.domain.BoaExamineDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 审批管理Mapper接口
 * 
 * @author bamboo
 * @date 2024-03-11
 */
public interface BoaExamineMapper extends BaseMapper<BoaExamineDO>
{
    /**
     * 查询审批管理
     * 
     * @param id 审批管理主键
     * @return 审批管理
     */
    public BoaExamineDO selectBoaExamineById(Integer id);

    /**
     * 查询审批管理列表
     * 
     * @param boaExamine 审批管理
     * @return 审批管理集合
     */
    public List<BoaExamineDO> selectBoaExamineList(BoaExamineDO boaExamine);

    /**
     * 新增审批管理
     * 
     * @param boaExamine 审批管理
     * @return 结果
     */
    public int insertBoaExamine(BoaExamineDO boaExamine);

    /**
     * 修改审批管理
     * 
     * @param boaExamine 审批管理
     * @return 结果
     */
    public int updateBoaExamine(BoaExamineDO boaExamine);

    /**
     * 删除审批管理
     * 
     * @param id 审批管理主键
     * @return 结果
     */
    public int deleteBoaExamineById(Integer id);

    /**
     * 批量删除审批管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBoaExamineByIds(Integer[] ids);
}
