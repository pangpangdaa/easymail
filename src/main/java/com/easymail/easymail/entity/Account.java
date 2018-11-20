package com.easymail.easymail.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Account implements Serializable{
    private String host;
    private Integer id;
    //这个是邮箱登陆密码
    private String password;
    private String userName;
    private String address;

    //这个是easymail的登陆密码
    private String passwd;
}
