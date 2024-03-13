package com.bamboo.boa.mapper;

import java.util.List;
import com.bamboo.boa.domain.BoaEmpFeedbackDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 员工反馈Mapper接口
 * 
 * @author bamboo
 * @date 2024-03-13
 */
@Repository
public interface BoaEmpFeedbackMapper extends BaseMapper<BoaEmpFeedbackDO>
{
    /**
     * 查询员工反馈
     * 
     * @param id 员工反馈主键
     * @return 员工反馈
     */
    public BoaEmpFeedbackDO selectBoaEmpFeedbackById(Integer id);

    /**
     * 查询员工反馈列表
     * 
     * @param boaEmpFeedback 员工反馈
     * @return 员工反馈集合
     */
    public List<BoaEmpFeedbackDO> selectBoaEmpFeedbackList(BoaEmpFeedbackDO boaEmpFeedback);

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
     * 删除员工反馈
     * 
     * @param id 员工反馈主键
     * @return 结果
     */
    public int deleteBoaEmpFeedbackById(Integer id);

    /**
     * 批量删除员工反馈
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBoaEmpFeedbackByIds(Integer[] ids);
}
