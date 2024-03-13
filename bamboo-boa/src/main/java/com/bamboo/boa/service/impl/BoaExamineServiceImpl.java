package com.bamboo.boa.service.impl;

import java.util.*;

import com.alibaba.fastjson2.JSON;
import com.bamboo.boa.domain.BoaApproveDO;
import com.bamboo.boa.domain.vo.BoaExamineVO;
import com.bamboo.boa.service.IBoaApproveService;
import com.bamboo.common.core.domain.entity.SysUser;
import com.bamboo.common.core.domain.model.LoginUser;
import com.bamboo.common.utils.SecurityUtils;
import com.bamboo.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bamboo.boa.mapper.BoaExamineMapper;
import com.bamboo.boa.domain.BoaExamineDO;
import com.bamboo.boa.service.IBoaExamineService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 审批管理Service业务层处理
 *
 * @author bamboo
 * @date 2024-03-11
 */
@Service
public class BoaExamineServiceImpl extends ServiceImpl<BoaExamineMapper, BoaExamineDO> implements IBoaExamineService {
    @Resource
    private BoaExamineMapper examineMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private IBoaApproveService approveService;

    /**
     * 查询审批管理
     *
     * @param id 审批管理主键
     * @return 审批管理
     */
    @Override
    public BoaExamineVO selectBoaExamineById(Integer id) {
        BoaExamineDO boaExamineDO = examineMapper.selectBoaExamineById(id);
        return converter(boaExamineDO);
    }

    /**
     * 查询审批管理列表
     *
     * @param boaExamine 审批管理
     * @return 审批管理
     */
    @Override
    public List<BoaExamineVO> selectBoaExamineList(BoaExamineVO boaExamine) {
        BoaExamineDO boaExamineDO = new BoaExamineDO();
        BeanUtils.copyProperties(boaExamine, boaExamineDO);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        boaExamineDO.setSubmitterId(loginUser.getUserId());

        List<BoaExamineDO> boaExamineDOS = examineMapper.selectBoaExamineList(boaExamineDO);

        List<BoaExamineVO> vos = new ArrayList<>();

        boaExamineDOS.forEach(e -> {
            vos.add(converter(e));
        });
        return vos;
    }

    public BoaExamineVO converter(BoaExamineDO boaExamineDO) {
        BoaExamineVO vo = new BoaExamineVO();

        BeanUtils.copyProperties(boaExamineDO, vo);
        SysUser sysUser = userService.selectUserById(boaExamineDO.getSubmitterId());
        vo.setSubmitName(sysUser.getNickName());
        return vo;
    }

    /**
     * 新增审批管理
     *
     * @param boaExamineVO 审批管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBoaExamine(BoaExamineVO boaExamineVO) {
        String submitName = boaExamineVO.getSubmitName();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        BoaExamineDO insert = new BoaExamineDO();
        insert.setSubmitterId(user.getUserId());
        insert.setApprovalType(boaExamineVO.getApprovalType());
        insert.setMessage(boaExamineVO.getMessage());
        insert.setStatus(0);
        examineMapper.insertBoaExamine(insert);
        Integer examineId = insert.getId();
        relevanceApprove(examineId, insert);
        return examineId;
    }

    private void relevanceApprove(Integer examineId, BoaExamineDO insert) {
        BoaApproveDO boaApproveDO = new BoaApproveDO();
        boaApproveDO.setExamineId(examineId);
        boaApproveDO.setStatus(insert.getStatus());
        Map<String, Object> msg = new HashMap<>();
        msg.put("approvalType", insert.getApprovalType());
        msg.put("message", insert.getMessage());
        boaApproveDO.setMsg(JSON.toJSONString(msg));
        approveService.insertBoaApprove(boaApproveDO);
    }

    /**
     * 修改审批管理
     *
     * @param boaExamine 审批管理
     * @return 结果
     */
    @Override
    public int updateBoaExamine(BoaExamineDO boaExamine) {

        return examineMapper.updateBoaExamine(boaExamine);
    }

    /**
     * 批量删除审批管理
     *
     * @param ids 需要删除的审批管理主键
     * @return 结果
     */
    @Override
    public int deleteBoaExamineByIds(Integer[] ids) {
        return examineMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除审批管理信息
     *
     * @param id 审批管理主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBoaExamineById(Integer id) {
        int result = examineMapper.deleteBoaExamineById(id);

        BoaApproveDO approveDO = approveService.selectApproveByExamineId(id);
        approveService.removeById(approveDO);
        return result;
    }

    @Override
    public BoaExamineDO selectBoaExamineDOById(Integer examineId) {
        return examineMapper.selectById(examineId);
    }
}
