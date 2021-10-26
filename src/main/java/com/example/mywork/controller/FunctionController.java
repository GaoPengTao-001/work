package com.example.mywork.controller;


import com.example.mywork.entity.Function;
import com.example.mywork.service.FunctionService;
import com.example.mywork.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/function")
public class FunctionController {

    @Resource
    private FunctionService functionService;

    /**
     * 日志打印
     */
    private static final Logger log = LoggerFactory.getLogger(FunctionController.class);

    @RequestMapping("/getFun")
    @ResponseBody
    public List<Function> getFun() {
        List<Function> functionList = null;
        try {
            functionList = functionService.selectFun();
            return functionList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("FunctionController.getFun查询菜单异常", e);
        }
        return functionList;
    }
}
