package com.example.mywork.mapper;


import com.example.mywork.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectUser(String id);


}
