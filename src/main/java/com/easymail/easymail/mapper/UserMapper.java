package com.easymail.easymail.mapper;

import com.easymail.easymail.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User findUserById(@Param("id") int id);

    void createUsers(List<User> users);

    List<User> findAll();
}
