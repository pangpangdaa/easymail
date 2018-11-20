package com.easymail.easymail.service;

import com.easymail.easymail.entity.Account;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MailService{

    Message[] getMessages() throws Exception;
    Message[] getMessagesByAccount(Account account) throws Exception;
    List<Message> getFilterdMessages(String title, Date date) throws Exception;
    void collectMissonInfo(String title,Account account);
    Map<String,List<String>> checkMissionInfo(String title,Account account);
    void compressMailsByTitle(String title);



}
