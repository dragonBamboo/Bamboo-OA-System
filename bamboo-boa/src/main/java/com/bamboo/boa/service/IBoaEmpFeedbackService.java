package com.bamboo.boa.service;

import java.util.List;
import com.bamboo.boa.domain.BoaEmpFeedbackDO;
import com.bamboo.boa.domain.vo.BoaEmpFeedbackVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 员工反馈Service接口
 * 
 * @author bamboo
 * @date 2024-03-13
 */
public interface IBoaEmpFeedbackService extends IService<BoaEmpFeedbackDO>
{
    /**
     * 查询员工反馈
     * 
     * @param id 员工反馈主键
     * @return 员工反馈
     */
    public BoaEmpFeedbackVO selectBoaEmpFeedbackById(Integer id);

    /**
     * 查询员工反馈列表
     *
     * @param boaEmpFeedback 员工反馈
     * @return 员工反馈集合
     */
    public List<BoaEmpFeedbackVO> selectBoaEmpFeedbackList(BoaEmpFeedbackVO boaEmpFeedback);

    /**
     * 新增员工反馈
     * 
     * @param boaEmpFeedback 员工反馈
     * @return 结果
     */
    public int insertBoaEmpFeedback(BoaEmpFeedbackDO boaEmpFeedback);

    /**
     * 修改员工反馈
     * 
     * @param boaEmpFeedback 员工反馈
     * @return 结果
     */
    public int updateBoaEmpFeedback(BoaEmpFeedbackDO boaEmpFeedback);

    /**
     * 批量删除员工反馈
     * 
     * @param ids 需要删除的员工反馈主键集合
     * @return 结果
     */
    public int deleteBoaEmpFeedbackByIds(Integer[] ids);

    /**
     * 删除员工反馈信息
     * 
     * @param id 员工反馈主键
     * @return 结果
     */
    public int deleteBoaEmpFeedbackById(Integer id);
}
