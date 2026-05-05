package com.sym.web.controller;

import com.sym.system.service.IMiniappLoginService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sym.common.result.AjaxResult;

@Slf4j
@RestController
@RequestMapping("/miniapp")
public class MiniappController {

    @Resource
    private IMiniappLoginService miniappLoginService;

}
