package com.bamboo.boa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bamboo.boa.domain.vo.BoaApproveVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bamboo.common.annotation.Log;
import com.bamboo.common.core.controller.BaseController;
import com.bamboo.common.core.domain.AjaxResult;
import com.bamboo.common.enums.BusinessType;
import com.bamboo.boa.domain.BoaApproveDO;
import com.bamboo.boa.service.IBoaApproveService;
import com.bamboo.common.utils.poi.ExcelUtil;
import com.bamboo.common.core.page.TableDataInfo;

/**
 * 审批Controller
 * 
 * @author bamboo
 * @date 2024-03-11
 */
@RestController
@RequestMapping("/boa/approve")
public class BoaApproveController extends BaseController
{
    @Autowired
    private IBoaApproveService boaApproveService;

    /**
     * 查询审批列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BoaApproveVO boaApprove)
    {
        startPage();
        List<BoaApproveVO> list = boaApproveService.selectBoaApproveList(boaApprove);
        return getDataTable(list);
    }

    // /**
    //  * 导出审批列表
    //  */
    // @Log(title = "审批", businessType = BusinessType.EXPORT)
    // @PostMapping("/export")
    // public void export(HttpServletResponse response, BoaApproveDO boaApprove)
    // {
    //     List<BoaApproveDO> list = boaApproveService.selectBoaApproveList(boaApprove);
    //     ExcelUtil<BoaApproveDO> util = new ExcelUtil<BoaApproveDO>(BoaApproveDO.class);
    //     util.exportExcel(response, list, "审批数据");
    // }

    /**
     * 获取审批详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(boaApproveService.selectBoaApproveById(id));
    }

    /**
     * 新增审批
     */
    @Log(title = "审批", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BoaApproveDO boaApprove)
    {
        return toAjax(boaApproveService.insertBoaApprove(boaApprove));
    }

    /**
     * 修改审批
     */
    @Log(title = "审批", businessType = BusinessType.AGREE)
    @PutMapping
    public AjaxResult edit(@RequestBody BoaApproveDO boaApprove)
    {
        return toAjax(boaApproveService.updateBoaApprove(boaApprove));
    }

    /**
     * 删除审批
     */
    @Log(title = "审批", businessType = BusinessType.REJECT)
	@DeleteMapping
    public AjaxResult remove(@RequestBody BoaApproveDO boaApprove)
    {
        return toAjax(boaApproveService.deleteBoaApproveById(boaApprove));
    }
}
