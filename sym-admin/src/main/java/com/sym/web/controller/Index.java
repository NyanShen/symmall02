package com.sym.web.controller;

import org.springframework.web.bind.annotation.*;
import sym.com.common.result.AjaxResult;

import java.util.HashMap;

@RestController
@RequestMapping("/index")
public class Index{

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
}
