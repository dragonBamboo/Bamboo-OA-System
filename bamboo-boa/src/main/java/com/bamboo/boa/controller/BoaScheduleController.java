package com.bamboo.boa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bamboo.boa.domain.vo.BoaScheduleVO;
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
import com.bamboo.boa.domain.BoaScheduleDO;
import com.bamboo.boa.service.BoaScheduleService;
import com.bamboo.common.utils.poi.ExcelUtil;
import com.bamboo.common.core.page.TableDataInfo;

/**
 * 日志Controller
 * 
 * @author bamboo
 * @date 2024-03-05
 */
@RestController
@RequestMapping("/boa/schedule")
public class BoaScheduleController extends BaseController
{
    @Autowired
    private BoaScheduleService boaScheduleService;

    /**
     * 查询日志列表
     */
    @PreAuthorize("@ss.hasPermi('boa:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(BoaScheduleDO boaSchedule)
    {
        startPage();
        List<BoaScheduleVO> list = boaScheduleService.selectBoaScheduleList(boaSchedule);
        return getDataTable(list);
    }

    /**
     * 导出日志列表
     */
    @PreAuthorize("@ss.hasPermi('boa:schedule:export')")
    @Log(title = "日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BoaScheduleDO boaSchedule)
    {
        List<BoaScheduleVO> list = boaScheduleService.selectBoaScheduleList(boaSchedule);
        ExcelUtil<BoaScheduleVO> util = new ExcelUtil<BoaScheduleVO>(BoaScheduleVO.class);
        util.exportExcel(response, list, "日志数据");
    }

    /**
     * 获取日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('boa:schedule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(boaScheduleService.selectBoaScheduleById(id));
    }

    /**
     * 新增日志
     */
    @PreAuthorize("@ss.hasPermi('boa:schedule:add')")
    @Log(title = "日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BoaScheduleDO boaSchedule)
    {
        return toAjax(boaScheduleService.insertBoaSchedule(boaSchedule));
    }

    /**
     * 修改日志
     */
    @PreAuthorize("@ss.hasPermi('boa:schedule:edit')")
    @Log(title = "日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BoaScheduleDO boaSchedule)
    {
        return toAjax(boaScheduleService.updateBoaSchedule(boaSchedule));
    }

    /**
     * 删除日志
     */
    @PreAuthorize("@ss.hasPermi('boa:schedule:remove')")
    @Log(title = "日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(boaScheduleService.deleteBoaScheduleByIds(ids));
    }
}
