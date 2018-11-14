package com.easymail.easymail.util;

import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserUtils {

    @Autowired
    UserMapper userMapper;

    private static Map<String,User> userMap = null;

    private static Map<String,User> idMap = null;

    public synchronized Map<String,User> getUserMap(){
        if(userMap==null){
            log.info("初始化userMap");
            userMap = new HashMap<String,User>();
            List<User> userList = userMapper.findAll();
           // log.info("userList size {}",userList.size());
            for(User user:userList){
              //  log.info("{}",user.getName());
                userMap.put(user.getName(),user);
            }
        }
        return userMap;
    }

    public synchronized Map<String,User> getIdMap(){
        if(userMap==null){
            log.info("初始化userMap");
            idMap = new HashMap<String,User>();
            List<User> userList = userMapper.findAll();
            // log.info("userList size {}",userList.size());
            for(User user:userList){
                //  log.info("{}",user.getName());
                idMap.put(String.valueOf(user.getId()),user);
            }
        }
        return idMap;
    }

}
