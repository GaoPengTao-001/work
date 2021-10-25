package com.example.mywork.mapper;


import com.example.mywork.entity.Function;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FunctionMapper {

    List<Function> selectFunction();


}
