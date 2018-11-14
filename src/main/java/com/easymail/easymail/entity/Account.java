package com.easymail.easymail.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Account implements Serializable{
    private String host;
    private Integer id;
    private String password;
    private String userName;
    private String address;
}
