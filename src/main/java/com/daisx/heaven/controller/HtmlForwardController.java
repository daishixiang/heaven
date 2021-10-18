package com.daisx.heaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: daisx
 * @Date: 2021/10/12 18:04
 */
@Controller
@RequestMapping("/html")
public class HtmlForwardController {

    /**
     * 转发页面
     * @param model
     * @param date
     * @return
     */
    @GetMapping("/forward")
    public String forward(ModelMap model,String date){
        model.addAttribute("date",date);
        return "attendance";
    }

}
