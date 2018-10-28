package com.easymail.easymail.util;

import com.easymail.easymail.entity.Mission;
import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.MissionMapper;
import com.easymail.easymail.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExcelUtils {


     public HSSFWorkbook generateHSSFWorkbook(List<Mission> missions,List<User> users,String title){
         HSSFWorkbook workbook = new HSSFWorkbook();
         HSSFSheet sheet = workbook.createSheet(title);
         HSSFRow firstRow = sheet.createRow(0);
         firstRow.createCell(0).setCellValue("序号");
         firstRow.createCell(1).setCellValue("姓名");
         firstRow.createCell(2).setCellValue("年龄");
         firstRow.createCell(3).setCellValue("性别");
         firstRow.createCell(4).setCellValue("身份证");
         firstRow.createCell(5).setCellValue("专业");
         firstRow.createCell(6).setCellValue("班级");
         firstRow.createCell(7).setCellValue("内容");
         firstRow.createCell(8).setCellValue("接收日期");
        for(int i=0;i<users.size();i++){
            User user = users.get(i);
            HSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getAge());
            row.createCell(3).setCellValue(user.getSex());
            row.createCell(4).setCellValue(user.getIdentity());
            row.createCell(5).setCellValue(user.getMajor());
            row.createCell(6).setCellValue(user.getClassName());
            for(Mission mission:missions){
                if(user.getName().equals(mission.getName())){
                    row.createCell(7).setCellValue(mission.getContent());
                    row.createCell(8).setCellValue(mission.getRecvDate().toString());
                    break;
                }
            }
         }
         return workbook;
     }
}
