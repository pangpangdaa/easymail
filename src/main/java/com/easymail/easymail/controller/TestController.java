package com.easymail.easymail.controller;

import com.easymail.easymail.entity.Mission;
import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.MissionMapper;
import com.easymail.easymail.mapper.UserMapper;
import com.easymail.easymail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class TestController {

    @Autowired
    MailService mailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MissionMapper missionMapper;

    @GetMapping("/usertest")
    @ResponseBody
    public Object testUser() throws Exception {
        mailService.collectMissonInfo("测试");
        return "ok";
    }

    @GetMapping("/checkMissionInfo")
    @ResponseBody
    public Object checkMissionInfo(){
       return mailService.checkMissionInfo("测试");
    }

    @GetMapping("/testMission")
    @ResponseBody
    public Object testMission(){
        Mission mission = new Mission();
        mission.setName("测试");
        mission.setContent("测试内容");
        mission.setTitle("测试标题");
        missionMapper.insertMission(mission);
        return "ok";
    }

    @GetMapping("/")
    @ResponseBody
    public Object test() throws Exception {
        mailService.getMessages();
        return "ok";
    }

    @GetMapping("/class")
    @ResponseBody
    public Object getClassMember(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("C:\\Users\\xuwei\\Desktop\\test.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = null;
            List<User> users=new ArrayList<User>();
            while((line=br.readLine())!=null){
                String[] fields=line.split(",");
                User user = new User();
                user.setName(fields[1]);
                user.setSex(fields[2]);
                user.setAge(Integer.valueOf(fields[3]));
                user.setIdentity(fields[4]);
                user.setMajor(fields[5]);
                user.setClassName(fields[6]);
                users.add(user);
                log.info("{}",line);
            }
            userMapper.createUsers(users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return "ok";
    }


    @GetMapping("/rank")
    public String getRank(){
        return "rank";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public Object findUserById(@PathVariable("id") int id){
        return userMapper.findUserById(id);
    }

    @GetMapping("/all")
    @ResponseBody
    public Object findAll(){
        return userMapper.findAll();
    }
}
