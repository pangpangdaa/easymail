package com.easymail.easymail.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Attachment implements Serializable{

    @Id
    private String id;
    private String userName;
    private String title;
    private File file;
    private String fileType;
    private Date createdDate;
}
