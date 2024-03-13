package com.bamboo.boa.service;

import java.util.List;
import com.bamboo.boa.domain.BoaExamineDO;
import com.bamboo.boa.domain.vo.BoaExamineVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 审批管理Service接口
 * 
 * @author bamboo
 * @date 2024-03-11
 */
public interface IBoaExamineService extends IService<BoaExamineDO>
{
    /**
     * 查询审批管理
     * 
     * @param id 审批管理主键
     * @return 审批管理
     */
    public BoaExamineVO selectBoaExamineById(Integer id);

    /**
     * 查询审批管理列表
     * 
     * @param boaExamine 审批管理
     * @return 审批管理集合
     */
    public List<BoaExamineVO> selectBoaExamineList(BoaExamineVO boaExamine);

    /**
     * 新增审批管理
     * 
     * @param boaExamine 审批管理
     * @return 结果
     */
    public int insertBoaExamine(BoaExamineVO boaExamine);

    /**
     * 修改审批管理
     * 
     * @param boaExamine 审批管理
     * @return 结果
     */
    public int updateBoaExamine(BoaExamineDO boaExamine);

    /**
     * 批量删除审批管理
     * 
     * @param ids 需要删除的审批管理主键集合
     * @return 结果
     */
    public int deleteBoaExamineByIds(Integer[] ids);

    /**
     * 删除审批管理信息
     * 
     * @param id 审批管理主键
     * @return 结果
     */
    public int deleteBoaExamineById(Integer id);

    BoaExamineDO selectBoaExamineDOById(Integer examineId);
}
