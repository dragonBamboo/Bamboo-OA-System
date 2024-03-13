package com.bamboo.boa.controller;

import com.bamboo.common.utils.sql.SmallUserVO;
import com.bamboo.common.core.domain.AjaxResult;
import com.bamboo.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static com.bamboo.common.core.domain.AjaxResult.success;

@Api(tags = "公共接口")
@RestController
@RequestMapping("/boa/common")
public class BoaCommonController {
    @Resource
    private ISysUserService userService;

    @GetMapping("/userSmall")
    @ApiOperation("用户简易信息")
    public AjaxResult getUserList() {
        List<SmallUserVO> userList = userService.selectSmallFormatUserList();
        return success(userList);
    }
}
