package com.bamboo.boa;

import com.bamboo.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@Api(tags = "boa测试管理")
@RestController
@RequestMapping("/boa")
public class BoaController {

    @GetMapping("/test")
    @ApiOperation("测试数据联通接口")
    public AjaxResult test() throws Exception {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("李伟", "不行");
        throw new Exception("加密异常");
        // return AjaxResult.success("测试成功",objectObjectHashMap);
    }

}
