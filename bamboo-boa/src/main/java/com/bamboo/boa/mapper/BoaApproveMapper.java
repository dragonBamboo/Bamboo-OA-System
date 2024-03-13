package com.bamboo.boa.mapper;

import java.util.List;
import com.bamboo.boa.domain.BoaApproveDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 审批Mapper接口
 * 
 * @author bamboo
 * @date 2024-03-11
 */
@Repository
public interface BoaApproveMapper extends BaseMapper<BoaApproveDO>
{
    /**
     * 查询审批
     * 
     * @param id 审批主键
     * @return 审批
     */
    public BoaApproveDO selectBoaApproveById(Long id);

    /**
     * 查询审批列表
     * 
     * @param boaApprove 审批
     * @return 审批集合
     */
    public List<BoaApproveDO> selectBoaApproveList(BoaApproveDO boaApprove);

    /**
     * 新增审批
     * 
     * @param boaApprove 审批
     * @return 结果
     */
    public int insertBoaApprove(BoaApproveDO boaApprove);

    /**
     * 修改审批
     * 
     * @param boaApprove 审批
     * @return 结果
     */
    public int updateBoaApprove(BoaApproveDO boaApprove);

    /**
     * 删除审批
     * 
     * @param id 审批主键
     * @return 结果
     */
    public int deleteBoaApproveById(Long id);

    /**
     * 批量删除审批
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBoaApproveByIds(Long[] ids);

    BoaApproveDO selectByExamineId(Integer examineId);
}
