package com.bamboo.web.controller.system;

import java.util.List;
import java.util.Set;

import com.bamboo.common.utils.sign.RsaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bamboo.common.constant.Constants;
import com.bamboo.common.core.domain.AjaxResult;
import com.bamboo.common.core.domain.entity.SysMenu;
import com.bamboo.common.core.domain.entity.SysUser;
import com.bamboo.common.core.domain.model.LoginBody;
import com.bamboo.common.utils.SecurityUtils;
import com.bamboo.framework.web.service.SysLoginService;
import com.bamboo.framework.web.service.SysPermissionService;
import com.bamboo.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author bamboo
 */
@Api(tags = "登录验证")
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) throws Exception {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        // String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
        //         loginBody.getUuid());
        String token = null;
        try {
            // token = loginService.login(loginBody.getUsername(),
            //         RsaUtils.decryptByPrivateKey(loginBody.getPassword()), loginBody.getCode(), loginBody.getUuid());
            token = loginService.login(loginBody.getUsername(),
                    RsaUtils.decryptByPrivateKey(loginBody.getPassword()), null, loginBody.getUuid());
        } catch (Exception e) {
            throw new Exception("登录失败，请重新登录！");
        }
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
