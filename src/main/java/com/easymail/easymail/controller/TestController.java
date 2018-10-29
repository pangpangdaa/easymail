package com.easymail.easymail.controller;

import com.easymail.easymail.entity.Mission;
import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.MissionMapper;
import com.easymail.easymail.mapper.UserMapper;
import com.easymail.easymail.service.MailService;
import com.easymail.easymail.util.ExcelUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class TestController {
/*
    @Autowired
    MailService mailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ExcelUtils excelUtils;

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
            fileInputStream = new FileInputStream("C:\\Users\\huang\\Desktop\\test.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = null;
            List<User> users=new ArrayList<User>();
            while((line=br.readLine())!=null){
                log.info("line : {}",line);
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
            log.error("{}",Throwables.getStackTraceAsString(e));
        } finally {
            try {
                fileInputStream.close();
            }catch (Exception e){
                log.error("{}",Throwables.getStackTraceAsString(e));
            }

        }

        return "ok";
    }

    @GetMapping("/download/{title}")
    public String export(@PathVariable("title") String title, HttpSession session, HttpServletResponse response) throws IOException {
        List<User> users = userMapper.findAll();
        List<Mission> missions = missionMapper.getMissionsByTitle(title);
        HSSFWorkbook workbook = excelUtils.generateHSSFWorkbook(missions,users,title);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try{
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while(-1!=(bytesRead=bis.read(buff,0,buff.length))){
                bos.write(buff,0,bytesRead);
            }
        }catch (Exception e){
            log.error("{}",Throwables.getStackTraceAsString(e));
        }finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            }catch (Exception e){
                log.error("{}",Throwables.getStackTraceAsString(e));
            }
        }

        return null;
    }

    @GetMapping("/rank")
    public String getRank(){
        return "result";
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
    }*/
}
