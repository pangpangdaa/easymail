package com.easymail.easymail.impl;

import com.easymail.easymail.entity.Mission;
import com.easymail.easymail.entity.User;
import com.easymail.easymail.mapper.MissionMapper;
import com.easymail.easymail.service.MailService;
import com.easymail.easymail.util.UserUtils;
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
        e.printStackTrace();
    } finally {
      //  folder.close(false);
       // store.close();
    }
    return null;
    }

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

    public Map<String,List<String>> checkMissionInfo(String title){
        Map<String,User> userMap = new HashMap<>(userUtils.getUserMap());
        List<Mission> missions = missionMapper.getMissionsByTitle(title);
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

    @Override
    public void collectMissonInfo(String title) {
        try {
            List<Message> messageList = getFilterdMessages(title, null);
            Map<String, User> userMap = userUtils.getUserMap();
            List<Mission> missions = new ArrayList<>();
            for (Message message : messageList) {
                String userName = message.getSubject().split("_")[0];
                Mission existMission = missionMapper.getMissionByTitleAndName(title, userName);
                if (existMission != null) {
                    if (existMission.getRecvDate().compareTo(message.getReceivedDate()) < 0) {
                        log.info("{}有重复记录需要删除 {},{}", userName, existMission.getRecvDate(), message.getReceivedDate());
                        missionMapper.deleteMissionByTitleAndName(title, userName);
                    } else continue;
                }
                Multipart multipart = (Multipart) message.getContent();
                for (int j = 0; j < multipart.getCount(); j++) {
                    BodyPart part = multipart.getBodyPart(j);
                    String type = part.getContentType().split(";")[0];
                    if (type.equals("text/plain")) { //纯文本
                        String content = part.getContent().toString();
                        Mission mission = new Mission();
                        mission.setTitle(title);
                        mission.setContent(content);
                        mission.setName(userName);
                        mission.setRecvDate(message.getReceivedDate());
                        missions.add(mission);
                    }

                }
            }
            if (missions.size() != 0) {
                missionMapper.insertMissions(missions);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (folder != null) folder.close(false);
                if (store != null) store.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
