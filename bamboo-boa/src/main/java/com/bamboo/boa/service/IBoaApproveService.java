package com.bamboo.boa.service;

import java.util.List;
import com.bamboo.boa.domain.BoaApproveDO;
import com.bamboo.boa.domain.vo.BoaApproveVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 审批Service接口
 * 
 * @author bamboo
 * @date 2024-03-11
 */
public interface IBoaApproveService extends IService<BoaApproveDO>
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
    public List<BoaApproveVO> selectBoaApproveList(BoaApproveVO boaApprove);

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
     * 批量删除审批
     * 
     * @param ids 需要删除的审批主键集合
     * @return 结果
     */
    public int deleteBoaApproveByIds(Long[] ids);

    /**
     * 驳回审批信息
     * 
     * @param boaApprove 审批主键
     * @return 结果
     */
    public int deleteBoaApproveById(BoaApproveDO boaApprove);

    BoaApproveDO selectApproveByExamineId(Integer examineId);
}
