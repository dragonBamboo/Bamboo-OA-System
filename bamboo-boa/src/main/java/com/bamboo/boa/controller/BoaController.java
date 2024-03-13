package com.bamboo.boa.controller;

import com.bamboo.boa.domain.BoaTestDO;
import com.bamboo.boa.service.BoaTestService;
import com.bamboo.common.core.controller.BaseController;
import com.bamboo.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Api(tags = "boa测试管理")
@RestController
@RequestMapping("/boa")
@Slf4j
public class BoaController extends BaseController {

    @Resource
    private BoaTestService boaTestService;

    @GetMapping("/test")
    @ApiOperation("测试数据联通接口")
    public AjaxResult test() throws Exception {
        List<BoaTestDO> list = boaTestService.list();
        log.info("list: {}",list);
        return AjaxResult.success(list);
    }

}
