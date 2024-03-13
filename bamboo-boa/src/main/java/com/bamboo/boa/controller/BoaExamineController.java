package com.bamboo.boa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bamboo.boa.domain.vo.BoaExamineVO;
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
import com.bamboo.boa.domain.BoaExamineDO;
import com.bamboo.boa.service.IBoaExamineService;
import com.bamboo.common.utils.poi.ExcelUtil;
import com.bamboo.common.core.page.TableDataInfo;

/**
 * 审批管理Controller
 * 
 * @author bamboo
 * @date 2024-03-11
 */
@RestController
@RequestMapping("/boa/examine")
public class BoaExamineController extends BaseController
{
    @Autowired
    private IBoaExamineService boaExamineService;

    /**
     * 查询审批管理列表
     */
    @PreAuthorize("@ss.hasPermi('boa:examine:list')")
    @GetMapping("/list")
    public TableDataInfo list(BoaExamineVO boaExamine)
    {
        startPage();
        List<BoaExamineVO> list = boaExamineService.selectBoaExamineList(boaExamine);
        return getDataTable(list);
    }

    /**
     * 导出审批管理列表
     */
    @PreAuthorize("@ss.hasPermi('boa:examine:export')")
    @Log(title = "审批管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BoaExamineDO boaExamine)
    {
        // List<BoaExamineDO> list = boaExamineService.selectBoaExamineList(boaExamine);
        // ExcelUtil<BoaExamineDO> util = new ExcelUtil<BoaExamineDO>(BoaExamineDO.class);
        // util.exportExcel(response, list, "审批管理数据");
    }

    /**
     * 获取审批管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('boa:examine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(boaExamineService.selectBoaExamineById(id));
    }

    /**
     * 新增审批管理
     */
    @PreAuthorize("@ss.hasPermi('boa:examine:add')")
    @Log(title = "审批管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BoaExamineVO boaExamine)
    {
        return toAjax(boaExamineService.insertBoaExamine(boaExamine));
    }

    /**
     * 修改审批管理
     */
    @PreAuthorize("@ss.hasPermi('boa:examine:edit')")
    @Log(title = "审批管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BoaExamineDO boaExamine)
    {
        return toAjax(boaExamineService.updateBoaExamine(boaExamine));
    }

    /**
     * 删除审批管理
     */
    @PreAuthorize("@ss.hasPermi('boa:examine:remove')")
    @Log(title = "审批管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(boaExamineService.deleteBoaExamineById(id));
    }
}
