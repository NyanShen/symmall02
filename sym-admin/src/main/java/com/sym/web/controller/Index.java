package com.sym.web.controller;

import com.sym.system.domain.BaseUser;
import com.sym.system.service.IBaseUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import sym.com.common.result.AjaxResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/index")
public class Index{

    @Resource
    private IBaseUserService baseUserService;

    @GetMapping("/hello")
    public AjaxResult hello() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "sym");
        return AjaxResult.success(map);
    }
    /**
     * 接收URL带参数
     * /index/get?name=sym
     */
    @GetMapping("/get")
    public AjaxResult getName(@RequestParam(value = "name", required = false) String name) {
        return AjaxResult.success("hello " + name);
    }
    /**
     * 接收Path variable
     */
    @GetMapping("/path/{name}")
    public AjaxResult getNameByPathVar(@PathVariable("name") String name) {
        return AjaxResult.success("hello " + name);
    }

    @GetMapping("/baseuser/list")
    public AjaxResult getBaseUserList() {
        List<BaseUser> list = baseUserService.list();
        return AjaxResult.success(list);
    }

    @GetMapping("/baseuser/{userId}")
    public AjaxResult getBaseUser(@PathVariable("userId") Long userId) {
        BaseUser baseUser = baseUserService.getById(userId);
        return AjaxResult.success(baseUser);
    }
}
