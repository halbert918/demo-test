package com.test.controller;

import com.test.dubbo.custom.TestCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by min on 2015/3/11.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestCustom testCustom;

    @RequestMapping("/test.html")
    public String test() {
        testCustom.operOrder();
        return "/test.jsp";
    }

}
