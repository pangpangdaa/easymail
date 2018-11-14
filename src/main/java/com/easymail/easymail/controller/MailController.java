package com.easymail.easymail.controller;

import com.easymail.easymail.entity.Account;
import com.easymail.easymail.entity.Mission;
import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.MissionMapper;
import com.easymail.easymail.mapper.UserMapper;
import com.easymail.easymail.service.MailService;
import com.easymail.easymail.util.ExcelUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MissionMapper missionMapper;

    @Autowired
    ExcelUtils excelUtils;

    @GetMapping("/checkMissionInfo/{title}")
    public String checkMissionInfo(@PathVariable("title") String title,Model model,HttpSession session){
        Account account = (Account)session.getAttribute("CURRENT_USER");
        if(account==null){
            return "redirect:/login";
        }
        mailService.collectMissonInfo(title,account);
        Map<String,List<String>> result = mailService.checkMissionInfo(title,account);
        model.addAttribute("result",result);
        model.addAttribute("title",title);
        return "result";
    }

    @GetMapping("/instruction")
    public String instuction(){
        return "instruction";
    }


    @DeleteMapping("/delete/{title}")
    public String deleteMissionByTitle(@PathVariable("title") String title,HttpSession session){
        Account account =(Account) session.getAttribute("CURRENT_USER");
        if(account==null){
            return "redirect:/login";
        }
        if(StringUtils.isEmpty(title)) return "firstpage";
        missionMapper.deleteMissionByTitle(title,account.getId());
        return "redirect:/mail";
    }

    @GetMapping
    public String firstpage(Model model,HttpSession session){
        Account account = (Account)session.getAttribute("CURRENT_USER");
        if(account==null){
            return "redirect:/login";
        }
        model.addAttribute("missions",missionMapper.getAllMissionTitles(account.getId()));
        return "firstpage";
    }

    @GetMapping("/test")
    @ResponseBody
    public Object test1(){
    String str="1                2                    3";
        return str.replaceAll(" +"," ");
    }

    @GetMapping("/search")
    public String searchMissionInfo(@RequestParam("words") String words,Model model,HttpSession session){
        Account account =(Account) session.getAttribute("CURRENT_USER");
        if(account==null){
            return "redirect:/login";
        }
        words = words.trim();
        mailService.collectMissonInfo(words,account);
        Map<String,List<String>> result = mailService.checkMissionInfo(words,account);
        model.addAttribute("result",result);
        model.addAttribute("title",words);
        return "result";
    }

    @GetMapping("/classinfo")
    public String getClassinfo(Model model){
        List<User> users = userMapper.findAll();
        model.addAttribute("users",users);
        return "classinfo";
    }


    @GetMapping("/download/{title}")
    public String export(@PathVariable("title") String title, HttpSession session, HttpServletResponse response) throws IOException {
        Account account = (Account)session.getAttribute("CURRENT_USER");
        if(account==null){
            return "redirect:/login";
        }
        List<User> users = userMapper.findAll();
        List<Mission> missions = missionMapper.getMissionsByTitle(title,account.getId());
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
                e.printStackTrace();
            }
        }

        return null;
    }
}
