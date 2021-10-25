package com.example.mywork.controller;


import com.example.mywork.entity.Function;
import com.example.mywork.service.FunctionService;
import com.example.mywork.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/function")
public class FunctionController {

    @Resource
    private FunctionService functionService;

    @RequestMapping("getFun")
    public List<Function> getFun() {
        return functionService.selectFun();
    }
}
