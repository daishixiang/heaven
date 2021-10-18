package com.daisx.heaven.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: daisx
 * @Date: 2021/10/12 18:04
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试
     * @param test
     * @return
     */
    @ResponseBody
    @GetMapping("/test")
    public String test(String test){
        return test;
    }
}
