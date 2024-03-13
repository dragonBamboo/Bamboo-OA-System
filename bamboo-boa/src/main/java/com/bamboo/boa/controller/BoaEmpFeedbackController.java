package com.bamboo.boa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bamboo.boa.domain.vo.BoaEmpFeedbackVO;
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
import com.bamboo.boa.domain.BoaEmpFeedbackDO;
import com.bamboo.boa.service.IBoaEmpFeedbackService;
import com.bamboo.common.utils.poi.ExcelUtil;
import com.bamboo.common.core.page.TableDataInfo;

/**
 * 员工反馈Controller
 * 
 * @author bamboo
 * @date 2024-03-13
 */
@RestController
@RequestMapping("/boa/feedback")
public class BoaEmpFeedbackController extends BaseController
{
    @Autowired
    private IBoaEmpFeedbackService boaEmpFeedbackService;

    /**
     * 查询员工反馈列表
     */
    @PreAuthorize("@ss.hasPermi('boa:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(BoaEmpFeedbackVO boaEmpFeedback)
    {
        startPage();
        List<BoaEmpFeedbackVO> list = boaEmpFeedbackService.selectBoaEmpFeedbackList(boaEmpFeedback);
        return getDataTable(list);
    }

    /**
     * 导出员工反馈列表
     */
    // @PreAuthorize("@ss.hasPermi('boa:feedback:export')")
    // @Log(title = "员工反馈", businessType = BusinessType.EXPORT)
    // @PostMapping("/export")
    // public void export(HttpServletResponse response, BoaEmpFeedbackDO boaEmpFeedback)
    // {
    //     List<BoaEmpFeedbackDO> list = boaEmpFeedbackService.selectBoaEmpFeedbackList(boaEmpFeedback);
    //     ExcelUtil<BoaEmpFeedbackDO> util = new ExcelUtil<BoaEmpFeedbackDO>(BoaEmpFeedbackDO.class);
    //     util.exportExcel(response, list, "员工反馈数据");
    // }

    /**
     * 获取员工反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('boa:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(boaEmpFeedbackService.selectBoaEmpFeedbackById(id));
    }

    /**
     * 新增员工反馈
     */
    @PreAuthorize("@ss.hasPermi('boa:feedback:add')")
    @Log(title = "员工反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BoaEmpFeedbackDO boaEmpFeedback)
    {
        return toAjax(boaEmpFeedbackService.insertBoaEmpFeedback(boaEmpFeedback));
    }

    /**
     * 修改员工反馈
     */
    @PreAuthorize("@ss.hasPermi('boa:feedback:edit')")
    @Log(title = "员工反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BoaEmpFeedbackDO boaEmpFeedback)
    {
        return toAjax(boaEmpFeedbackService.updateBoaEmpFeedback(boaEmpFeedback));
    }

    /**
     * 删除员工反馈
     */
    @PreAuthorize("@ss.hasPermi('boa:feedback:remove')")
    @Log(title = "员工反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(boaEmpFeedbackService.deleteBoaEmpFeedbackByIds(ids));
    }
}
