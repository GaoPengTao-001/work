package com.example.mywork.service;

import com.example.mywork.entity.Function;
import com.example.mywork.entity.User;
import com.example.mywork.mapper.FunctionMapper;
import com.example.mywork.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionService {
    @Resource
    FunctionMapper functionMapper;

    /**
     * 日志打印
     */
    private static final Logger log = LoggerFactory.getLogger(FunctionService.class);

    public List<Function> selectFun() {
        List<Function> functionList = null;
        try {
            functionList = functionMapper.selectFunction();
            return functionList;
        } catch (Exception e) {
            log.error("FunctionService.selectFun异常", e);
        }
        return functionList;
    }

}
