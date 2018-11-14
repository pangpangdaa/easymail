package com.easymail.easymail.impl;

import com.easymail.easymail.entity.Account;
import com.easymail.easymail.entity.Mission;
import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.MissionMapper;
import com.easymail.easymail.service.MailService;
import com.easymail.easymail.util.Html2Text;
import com.easymail.easymail.util.MessageUtils;
import com.easymail.easymail.util.UserUtils;
import com.google.common.base.Throwables;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.*;

/**
 *
 * JavaMail JavaMail发送和接收邮件API（详解）
 */

@Component
@Slf4j
public class MailServiceImpl implements MailService{

    @Autowired
    MissionMapper missionMapper;

    @Autowired
    UserUtils userUtils;

    @Value("${info.host}")
    private String host;

    @Value("${info.username}")
    private String username;

   @Value("${info.password}")
    private String password;
    private Store store = null;
    private Folder folder = null;

    @Override
    public Message[] getMessagesByAccount(Account account) throws Exception{
        try {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imap");
            log.info("{},{},{}",account.getUserName(),account.getPassword(),account.getHost());
            store.connect(account.getHost(), account.getUserName(),account.getPassword());
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message message[] = folder.getMessages();
/*        for (int i = 0, n = message.length; i < n; i++) {
            String subject=message[i].getSubject();
            if(subject.indexOf("论文")!=-1) {
                log.info("{}:{}", i, message[i].getSubject());
                Multipart multipart =(Multipart)message[i].getContent();
                int count=multipart.getCount();
                for(int j=0;j<count;j++){
                    BodyPart part = multipart.getBodyPart(j);
                    String type = part.getContentType().split(";")[0];
                    log.info("type is {}",type);
                    if(type.equals("text/plain")){
                        log.info("纯文本:{}",part.getContent().toString());
                    } else {
                        log.info("html:{}",part.getContent().toString());
                    }

                }
                log.info("{}的内容：{}", i, message[i].getContent());
            }
        }*/
            log.info("total messages {}",message.length);
            return message;
        }catch (Exception e){
            log.error("{}",Throwables.getStackTraceAsString(e));
            try {
                folder.close(false);
                store.close();
            }catch (Exception ex){
                log.info("关闭邮箱错误");
            }
        }
        return null;
    }


    /**
     * https://www.cnblogs.com/liuyitian/p/4051922.html
     * @throws Exception
     */
    @Override
    public Message[] getMessages() throws Exception{
    try {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        store = session.getStore("imap");
        log.info("{},{},{}",username,password,host);
        store.connect(host, username, password);
        folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message message[] = folder.getMessages();
/*        for (int i = 0, n = message.length; i < n; i++) {
            String subject=message[i].getSubject();
            if(subject.indexOf("论文")!=-1) {
                log.info("{}:{}", i, message[i].getSubject());
                Multipart multipart =(Multipart)message[i].getContent();
                int count=multipart.getCount();
                for(int j=0;j<count;j++){
                    BodyPart part = multipart.getBodyPart(j);
                    String type = part.getContentType().split(";")[0];
                    log.info("type is {}",type);
                    if(type.equals("text/plain")){
                        log.info("纯文本:{}",part.getContent().toString());
                    } else {
                        log.info("html:{}",part.getContent().toString());
                    }

                }
                log.info("{}的内容：{}", i, message[i].getContent());
            }
        }*/
        log.info("total messages {}",message.length);
        return message;
    }catch (Exception e){
        log.error("{}",Throwables.getStackTraceAsString(e));
        try {
            folder.close(false);
            store.close();
        }catch (Exception ex){
            log.info("关闭邮箱错误");
        }
    }
    return null;
    }


    //采用主题名字精确匹配，该匹配方式暂时不用
    @Override
    public List<Message> getFilterdMessages(String title, Date date) throws Exception {
        Message[] messages = getMessages();
        List<Message> messageList = new ArrayList<>();
        for(int i=0;i<messages.length;i++){
            String subject = messages[i].getSubject();
            Date recDate = messages[i].getReceivedDate();
            if(subject.indexOf(title)!=-1 && (date==null || recDate.after(date))){
                messageList.add(messages[i]);
            }
        }
        return messageList;
    }



    public Map<String,List<String>> checkMissionInfo(String title,Account account){
        log.info("任务完成情况检查");
        Map<String,User> userMap = new HashMap<>(userUtils.getUserMap());
        List<Mission> missions = missionMapper.getMissionsByTitle(title,account.getId());
        List<String> fullFilled = new ArrayList<>();
        List<String> unFullFilled = new ArrayList<>();
        Map<String,List<String>> result = new HashMap<String,List<String>>();
        for(Mission mission:missions){
            if(userMap.remove(mission.getName())!=null){
                fullFilled.add(mission.getName());
            }
        }
        for(Map.Entry entry:userMap.entrySet()){
            unFullFilled.add((String)entry.getKey());
        }
        result.put("fullfilled",fullFilled);
        result.put("unfullfilled",unFullFilled);
        return result;
    }

    //判断是否是目标邮件
    private User judgeMail(Message message,String title,Date date){
        try {
        }catch (Exception e){
            log.info("utils 解析不了");
        }
        try {
            //判断标题和用户
            String[] splits = null;
            splits = message.getSubject().split("_");
            if (splits.length < 3) {
                splits = message.getSubject().split("-");
                if (splits.length < 3) {
                    splits = message.getSubject().split(" ");
                    if (splits.length < 3) {
                        splits = message.getSubject().split("/");
                        if (splits.length < 3) {
                            splits = message.getSubject().split("——");
                            if (splits.length < 3) return null;
                        }
                    }
                }
            }

            Map<String, User> userMap = userUtils.getUserMap();
            Map<String, User> idMap = userUtils.getIdMap();
            for (String str : splits) {
                if (str.trim().equals(title)) {
                    for (String nameOrId : splits) {
                        if (userMap.containsKey(nameOrId.trim())) return userMap.get(nameOrId);
                        else if (idMap.containsKey(nameOrId.trim())) return idMap.get(nameOrId);
                    }
                }
            }
            return null;
        }catch (Exception e){
            log.error("{}",Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public void collectMissonInfo(String title,Account account) {
        try {
           Message[] messages = getMessagesByAccount(account);
            List<Mission> missions = new ArrayList<>();
            for (Message message : messages) {
                User user = judgeMail(message, title, null);

                if (user == null) continue;
                log.info("查找到可能是任务邮件，用户{}", user.getName());
                Mission existMission = missionMapper.getMissionByTitleAndName(title, user.getName(),account.getId());
                if (existMission != null) {
                    if (existMission.getRecvDate().compareTo(message.getReceivedDate()) < 0) {
                        log.info("{}有重复记录需要删除 {},{}", user.getName(), existMission.getRecvDate(), message.getReceivedDate());
                        missionMapper.deleteMissionByTitleAndName(title, user.getName(),account.getId());
                    } else continue;
                }
                try {
                    Multipart multipart = (Multipart) message.getContent();
                    log.info("message type{}",message.getContentType());
                    for (int j = 0; j < multipart.getCount(); j++) {
                        BodyPart part = multipart.getBodyPart(j);
                        String type = part.getContentType().split(";")[0];
                        if (type.equals("text/plain")) { //纯文本
                            String content = part.getContent().toString();
                            content = content.replaceAll("\\s*"," ");
                            Mission mission = new Mission();
                            mission.setTitle(title);
                            mission.setContent(content);
                            mission.setName(user.getName());
                            mission.setRecvDate(message.getReceivedDate());
                            log.info("新增完成任务{}", mission);
                            missions.add(mission);
                        }

                    }
                } catch (Exception e) {
                    log.info("邮件不是multipart格式，是{}", message.getContentType());

                    Mission mission = new Mission();
                    mission.setTitle(title);
                    if(message.getContentType().indexOf("html")!=-1){
                        String content=Html2Text.getContent(message.getContent().toString()).replaceAll("\\s*"," ");
                        mission.setContent(content);
                    } else {
                        String content=message.getContent().toString().replaceAll("\\s*"," ");
                        mission.setContent(content);
                    }
                    mission.setName(user.getName());
                    mission.setRecvDate(message.getReceivedDate());
                    log.info("新增完成任务{}", mission);
                    missions.add(mission);
                }
            }
            if (missions.size() != 0) {
                safeMissions(missions,account);
            }
        }catch (Exception e){

        }finally {
            try {
                if (folder != null) folder.close(false);
                if (store != null) store.close();
            }catch (Exception e){
                log.error("{}",Throwables.getStackTraceAsString(e));
            }
        }
    }

    private void safeMissions(List<Mission> missions,Account account){
        Long start = System.currentTimeMillis();
        for(Mission mission:missions){
            missionMapper.insertMission(mission,account.getId());
        }
        log.info("插入用时{}",System.currentTimeMillis()-start);
        //missionMapper.insertMissions(missions,account.getId());
    }
}
