package com.bamboo.boa.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bamboo.boa.domain.vo.BoaEmpFeedbackVO;
import com.bamboo.common.core.domain.entity.SysUser;
import com.bamboo.common.utils.DateUtils;
import com.bamboo.common.utils.SecurityUtils;
import com.bamboo.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bamboo.boa.mapper.BoaEmpFeedbackMapper;
import com.bamboo.boa.domain.BoaEmpFeedbackDO;
import com.bamboo.boa.service.IBoaEmpFeedbackService;

import javax.annotation.Resource;

/**
 * 员工反馈Service业务层处理
 *
 * @author bamboo
 * @date 2024-03-13
 */
@Service
public class BoaEmpFeedbackServiceImpl extends ServiceImpl<BoaEmpFeedbackMapper, BoaEmpFeedbackDO> implements IBoaEmpFeedbackService {
    @Autowired
    private BoaEmpFeedbackMapper boaEmpFeedbackMapper;

    @Resource
    private ISysUserService userService;

    /**
     * 查询员工反馈
     *
     * @param id 员工反馈主键
     * @return 员工反馈
     */
    @Override
    public BoaEmpFeedbackVO selectBoaEmpFeedbackById(Integer id) {
        BoaEmpFeedbackDO boaEmpFeedbackDO = boaEmpFeedbackMapper.selectBoaEmpFeedbackById(id);
        BoaEmpFeedbackVO vo = converter(boaEmpFeedbackDO);
        return vo;
    }

    /**
     * 查询员工反馈列表
     *
     * @param boaEmpFeedback 员工反馈
     * @return 员工反馈
     */
    @Override
    public List<BoaEmpFeedbackVO> selectBoaEmpFeedbackList(BoaEmpFeedbackVO boaEmpFeedback) {
        Long userId = SecurityUtils.getUserId();
        if (!(SecurityUtils.isAdmin(userId) || SecurityUtils.hasPermi("superadmin"))) {
            boaEmpFeedback.setUserId(userId);
        }
        List<BoaEmpFeedbackDO> dos = boaEmpFeedbackMapper.selectBoaEmpFeedbackList(boaEmpFeedback);

        List<BoaEmpFeedbackVO> vos = new ArrayList<>();
        dos.forEach(e->{
            vos.add(converter(e));
        });
        return vos;
    }

    private BoaEmpFeedbackVO converter(BoaEmpFeedbackDO boaEmpFeedbackDO) {
        BoaEmpFeedbackVO vo = new BoaEmpFeedbackVO();
        BeanUtils.copyProperties(boaEmpFeedbackDO,vo);
        SysUser sysUser = userService.selectUserById(vo.getUserId());
        vo.setNickName(sysUser.getNickName());
        return vo;
    }

    /**
     * 新增员工反馈
     *
     * @param boaEmpFeedback 员工反馈
     * @return 结果
     */
    @Override
    public int insertBoaEmpFeedback(BoaEmpFeedbackDO boaEmpFeedback) {
        Long userId = SecurityUtils.getUserId();
        boaEmpFeedback.setUserId(userId);
        return boaEmpFeedbackMapper.insert(boaEmpFeedback);
    }

    /**
     * 修改员工反馈
     *
     * @param boaEmpFeedback 员工反馈
     * @return 结果
     */
    @Override
    public int updateBoaEmpFeedback(BoaEmpFeedbackDO boaEmpFeedback) {
        boaEmpFeedback.setUpdateTime(DateUtils.getNowDate());
        return boaEmpFeedbackMapper.updateById(boaEmpFeedback);
    }

    /**
     * 批量删除员工反馈
     *
     * @param ids 需要删除的员工反馈主键
     * @return 结果
     */
    @Override
    public int deleteBoaEmpFeedbackByIds(Integer[] ids) {
        return boaEmpFeedbackMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除员工反馈信息
     *
     * @param id 员工反馈主键
     * @return 结果
     */
    @Override
    public int deleteBoaEmpFeedbackById(Integer id) {
        return boaEmpFeedbackMapper.deleteById(id);
    }
}
