package com.bamboo.boa.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bamboo.boa.domain.vo.BoaScheduleVO;
import com.bamboo.common.core.domain.entity.SysUser;
import com.bamboo.common.core.domain.model.LoginUser;
import com.bamboo.common.utils.DateUtils;
import com.bamboo.common.utils.SecurityUtils;
import com.bamboo.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bamboo.boa.mapper.BoaScheduleMapper;
import com.bamboo.boa.domain.BoaScheduleDO;
import com.bamboo.boa.service.BoaScheduleService;

import javax.annotation.Resource;

/**
 * 日志Service业务层处理
 * 
 * @author bamboo
 * @date 2024-03-05
 */
@Service
@Slf4j
public class BoaScheduleServiceImpl extends ServiceImpl<BoaScheduleMapper,BoaScheduleDO> implements BoaScheduleService
{
    @Resource
    private BoaScheduleMapper boaScheduleMapper;

    @Resource
    private ISysUserService userService;

    private BoaScheduleVO converter(BoaScheduleDO boaSchedule) {
        if (boaSchedule == null) {
            return null;
        }
        BoaScheduleVO vo = new BoaScheduleVO();
        BeanUtils.copyProperties(boaSchedule, vo);

        Date nowDate = DateUtils.getNowDate();

        if (vo.getStartTime() != null && vo.getStartTime().getTime() > nowDate.getTime()) {
            vo.setStatus(0);
        }else {
            vo.setStatus(1);
        }

        if (vo.getEndTime() != null && vo.getEndTime().getTime() < nowDate.getTime()) {
            vo.setStatus(2);
        }

        SysUser submitter = userService.selectUserById(vo.getSubmitterId());
        vo.setNickName(submitter.getNickName());
        return vo;
    }


    /**
     * 查询日志
     * 
     * @param id 日志主键
     * @return 日志
     */
    @Override
    public BoaScheduleVO selectBoaScheduleById(Long id)
    {
        BoaScheduleVO vo = converter(boaScheduleMapper.selectById(id));
        return vo;
    }

    /**
     * 查询日志列表
     *
     * @param boaSchedule 日志
     * @return 日志
     */
    @Override
    public List<BoaScheduleVO> selectBoaScheduleList(BoaScheduleDO boaSchedule)
    {
        List<BoaScheduleDO> boaScheduleDOS = boaScheduleMapper.selectBoaScheduleList(boaSchedule);

        List<BoaScheduleVO> vos = new ArrayList<>();

        boaScheduleDOS.forEach(e->{
            BoaScheduleVO vo = converter(e);
            if (vo != null) {
                vos.add(vo);
            }
        });
        return vos;
    }

    /**
     * 新增日志
     * 
     * @param boaSchedule 日志
     * @return 结果
     */
    @Override
    public int insertBoaSchedule(BoaScheduleDO boaSchedule)
    {
        log.info("boaSchedule: {}",boaSchedule);

        LoginUser loginUser = SecurityUtils.getLoginUser();

        boaSchedule.setSubmitterId(loginUser.getUserId());
        boaSchedule.setUpdateTime(DateUtils.getNowDate());
        return boaScheduleMapper.insert(boaSchedule);
    }

    /**
     * 修改日志
     * 
     * @param boaSchedule 日志
     * @return 结果
     */
    @Override
    public int updateBoaSchedule(BoaScheduleDO boaSchedule)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        boaSchedule.setSubmitterId(loginUser.getUserId());

        return boaScheduleMapper.updateById(boaSchedule);
    }

    /**
     * 批量删除日志
     * 
     * @param ids 需要删除的日志主键
     * @return 结果
     */
    @Override
    public int deleteBoaScheduleByIds(Long[] ids)
    {
        return boaScheduleMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除日志信息
     * 
     * @param id 日志主键
     * @return 结果
     */
    @Override
    public int deleteBoaScheduleById(Long id)
    {
        return boaScheduleMapper.deleteBoaScheduleById(id);
    }
}
