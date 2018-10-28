package com.easymail.easymail.service;

import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MailService{

    Message[] getMessages() throws Exception;
    List<Message> getFilterdMessages(String title, Date date) throws Exception;
    void collectMissonInfo(String title);
    Map<String,List<String>> checkMissionInfo(String title);




}
