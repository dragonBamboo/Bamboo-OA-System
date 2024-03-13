package com.bamboo.boa.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.bamboo.boa.domain.BoaExamineDO;
import com.bamboo.boa.domain.vo.BoaApproveVO;
import com.bamboo.boa.domain.vo.BoaExamineVO;
import com.bamboo.boa.service.IBoaExamineService;
import com.bamboo.common.core.domain.entity.SysUser;
import com.bamboo.common.utils.DateUtils;
import com.bamboo.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bamboo.boa.mapper.BoaApproveMapper;
import com.bamboo.boa.domain.BoaApproveDO;
import com.bamboo.boa.service.IBoaApproveService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 审批Service业务层处理
 * 
 * @author bamboo
 * @date 2024-03-11
 */
@Service
public class BoaApproveServiceImpl extends ServiceImpl<BoaApproveMapper,BoaApproveDO> implements IBoaApproveService
{
    @Autowired
    private BoaApproveMapper boaApproveMapper;

    @Resource
    private IBoaExamineService examineService;

    @Resource
    private ISysUserService userService;

    /**
     * 查询审批
     * 
     * @param id 审批主键
     * @return 审批
     */
    @Override
    public BoaApproveDO selectBoaApproveById(Long id)
    {
        return boaApproveMapper.selectBoaApproveById(id);
    }

    /**
     * 查询审批列表
     * 
     * @param boaApprove 审批
     * @return 审批
     */
    @Override
    public List<BoaApproveVO> selectBoaApproveList(BoaApproveVO boaApprove)
    {
        BoaApproveDO boaApproveDO = new BoaApproveDO();
        BeanUtils.copyProperties(boaApprove,boaApproveDO);
        List<BoaApproveDO> boaApproveDOS = boaApproveMapper.selectBoaApproveList(boaApproveDO);
        List<BoaApproveVO> vos = new ArrayList<>();
        boaApproveDOS.forEach(e->{
            if (e != null) {
                BoaApproveVO vo = new BoaApproveVO();
                BeanUtils.copyProperties(e,vo);
                BoaExamineDO examineDO = examineService.getById(vo.getExamineId());
                SysUser sysUser = userService.selectUserById(examineDO.getSubmitterId());
                if (sysUser != null && sysUser.getNickName() != null) {
                    vo.setSubmitName(sysUser.getNickName());
                }
                vos.add(vo);
            }
        });

        return vos;
    }

    /**
     * 新增审批
     * 
     * @param boaApprove 审批
     * @return 结果
     */
    @Override
    public int insertBoaApprove(BoaApproveDO boaApprove)
    {
        boaApprove.setCreateTime(DateUtils.getNowDate());
        return boaApproveMapper.insertBoaApprove(boaApprove);
    }

    /**
     * 修改审批【同意】
     * 
     * @param boaApprove 审批
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBoaApprove(BoaApproveDO boaApprove)
    {
        boaApprove.setStatus(1);
        BoaExamineDO boaExamineDO = examineService.selectBoaExamineDOById(boaApprove.getExamineId());
        boaExamineDO.setStatus(boaApprove.getStatus());
        examineService.updateBoaExamine(boaExamineDO);
        return boaApproveMapper.updateBoaApprove(boaApprove);
    }

    /**
     * 批量删除审批
     * 
     * @param ids 需要删除的审批主键
     * @return 结果
     */
    @Override
    public int deleteBoaApproveByIds(Long[] ids)
    {
        return boaApproveMapper.deleteBoaApproveByIds(ids);
    }

    /**
     * 删除审批信息
     * 
     * @param boaApprove 审批主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBoaApproveById(BoaApproveDO boaApprove)
    {
        boaApprove.setStatus(2);
        BoaExamineDO boaExamineDO = examineService.selectBoaExamineDOById(boaApprove.getExamineId());
        boaExamineDO.setStatus(boaApprove.getStatus());
        examineService.updateBoaExamine(boaExamineDO);
        return boaApproveMapper.updateBoaApprove(boaApprove);
        // return boaApproveMapper.deleteBoaApproveById(id);
    }

    @Override
    public BoaApproveDO selectApproveByExamineId(Integer examineId) {
        BoaApproveDO boaApproveDO = boaApproveMapper.selectByExamineId(examineId);
        return boaApproveDO;
    }
}
