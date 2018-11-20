package com.easymail.easymail.util;

import java.io.File;

public class FileHelper {
    public static boolean deleteFile(File dirFile){
        if(!dirFile.exists()){
            return false;
        }
        if(dirFile.isFile()){
            return dirFile.delete();
        } else {
            for(File file:dirFile.listFiles()){
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }

    public static boolean deleteFile(String dirPath){
        File file = new File(dirPath);
        return deleteFile(file);
    }
}
