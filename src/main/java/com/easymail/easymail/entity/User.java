package com.easymail.easymail.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class User implements Serializable{
    Integer id;
    String name;
    String sex;
    Integer age;
    String identity;
    String major;
    String className;
}
