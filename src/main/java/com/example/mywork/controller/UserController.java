package com.example.mywork.controller;


import com.example.mywork.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/testBoot")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable String id) {
        return userService.Sel(id).toString();
    }

    @RequestMapping("redirect/{jsession}")
    public String redirect(@PathVariable String jsession) {
        System.out.println(jsession);

//        return "redirect:";
        return jsession;
    }
}
