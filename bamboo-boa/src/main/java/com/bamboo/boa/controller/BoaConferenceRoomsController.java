package com.bamboo.boa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.bamboo.boa.domain.vo.BoaConferenceRoomsVO;
import com.bamboo.boa.domain.vo.ImgUrl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bamboo.common.annotation.Log;
import com.bamboo.common.core.controller.BaseController;
import com.bamboo.common.core.domain.AjaxResult;
import com.bamboo.common.enums.BusinessType;
import com.bamboo.boa.domain.BoaConferenceRoomsDO;
import com.bamboo.boa.service.IBoaConferenceRoomsService;
import com.bamboo.common.utils.poi.ExcelUtil;
import com.bamboo.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 会议室信息Controller
 *
 * @author bamboo
 * @date 2024-03-07
 */
@RestController
@Api(tags = "会议室管理")
@RequestMapping("/boa/rooms")
public class BoaConferenceRoomsController extends BaseController {
    @Autowired
    private IBoaConferenceRoomsService boaConferenceRoomsService;

    /**
     * 查询会议室信息列表
     */
    @PreAuthorize("@ss.hasPermi('boa:rooms:list')")
    @GetMapping("/list")
    public TableDataInfo list(BoaConferenceRoomsDO boaConferenceRooms) {
        startPage();
        List<BoaConferenceRoomsVO> list = boaConferenceRoomsService.selectBoaConferenceRoomsList(boaConferenceRooms);
        return getDataTable(list);
    }

    /**
     * 导出会议室信息列表
     */
    @PreAuthorize("@ss.hasPermi('boa:rooms:export')")
    @Log(title = "会议室信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BoaConferenceRoomsDO boaConferenceRooms) {
        List<BoaConferenceRoomsVO> list = boaConferenceRoomsService.selectBoaConferenceRoomsList(boaConferenceRooms);
        ExcelUtil<BoaConferenceRoomsVO> util = new ExcelUtil<BoaConferenceRoomsVO>(BoaConferenceRoomsVO.class);
        util.exportExcel(response, list, "会议室信息数据");
    }

    /**
     * 获取会议室信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('boa:rooms:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(boaConferenceRoomsService.selectBoaConferenceRoomsById(id));
    }

    /**
     * 新增会议室信息
     */
    @PreAuthorize("@ss.hasPermi('boa:rooms:add')")
    @Log(title = "会议室信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BoaConferenceRoomsDO boaConferenceRooms) {
        return toAjax(boaConferenceRoomsService.insertBoaConferenceRooms(boaConferenceRooms));
    }

    /**
     * 修改会议室信息
     */
    @PreAuthorize("@ss.hasPermi('boa:rooms:edit')")
    @Log(title = "会议室信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BoaConferenceRoomsDO boaConferenceRooms) {
        return toAjax(boaConferenceRoomsService.updateBoaConferenceRooms(boaConferenceRooms));
    }

    /**
     * 删除会议室信息
     */
    @PreAuthorize("@ss.hasPermi('boa:rooms:remove')")
    @Log(title = "会议室信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(boaConferenceRoomsService.deleteBoaConferenceRoomsByIds(ids));
    }

    @PostMapping("/uploadImg")
    public AjaxResult uploadImg(@RequestParam("file") MultipartFile file) {
        Object result = boaConferenceRoomsService.uploadImg(file);
        return success(result);
    }

    @PostMapping("/imgUrl")
    public AjaxResult addImgUrl(@RequestBody ImgUrl imgUrlVO) {
        boaConferenceRoomsService.addImgUrl(imgUrlVO);
        return success();
    }

    @ApiOperation("获取图片")
    @GetMapping("/imgUrl/{id}/{path}")
    public void getImgUrl(@PathVariable("id") Integer id, @PathVariable("path") String path,HttpServletResponse response) throws Exception {
        boaConferenceRoomsService.getImgUrl(id,path, response);
    }
}
