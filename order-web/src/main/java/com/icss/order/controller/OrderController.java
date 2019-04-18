package com.icss.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    @RequestMapping("orderInfo")
    @ResponseBody
    public String getOrderInfo(){
        return "orderInfo!";
    }
    @RequestMapping("orderInfo2")
    public String getOrderInfo2(){
        return "success";
    }
}
