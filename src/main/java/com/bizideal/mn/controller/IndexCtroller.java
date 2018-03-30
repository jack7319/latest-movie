package com.bizideal.mn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : liulq
 * @date: 创建时间: 2018/3/30 10:33
 * @version: 1.0
 * @Description:
 */
@Controller
public class IndexCtroller {

    @RequestMapping("/")
    public String hello() {
        return "index";
    }
}
