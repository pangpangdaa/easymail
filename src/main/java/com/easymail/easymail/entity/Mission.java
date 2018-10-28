package com.easymail.easymail.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Mission implements Serializable {
    Integer id;
    String title;
    String name;
    String content;
    Date recvDate;
}
