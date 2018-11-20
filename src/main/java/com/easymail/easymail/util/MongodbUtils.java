package com.easymail.easymail.util;

import com.easymail.easymail.config.GlobalConfig;
import com.easymail.easymail.entity.User;
import com.google.common.base.Throwables;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class MongodbUtils {


    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GlobalConfig globalConfig;

    private GridFSBucket gridFSBucket;

    @PostConstruct
    public void init(){
        log.info("创建gridFSBucket");
        this.gridFSBucket = GridFSBuckets.create(mongoTemplate.getMongoDbFactory().getDb());
    }

    public ObjectId saveFile(File file,String fileName,String fileType){
        InputStream inputStream = null;
       try {
           inputStream = new FileInputStream(file);
           return gridFsTemplate.store(inputStream, fileName, fileType);
       }catch (Exception e){
           log.info("{}",Throwables.getStackTraceAsString(e));
       }finally {
           try {
               if (inputStream != null) {
                   inputStream.close();
               }
           }catch (Exception ex){
               log.info("{}",Throwables.getStackTraceAsString(ex));
           }
       }
       return null;
    }


    public void getFiles(String fileName) throws IOException {
        GridFSFindIterable gridFSFindIterable = gridFsTemplate.find(Query.query(Criteria.where("filename").is(fileName)));
        MongoCursor<GridFSFile> mongoCursor = gridFSFindIterable.iterator();
        while(mongoCursor.hasNext()){
            GridFSFile gridFSFile = mongoCursor.next();
            downFile(globalConfig.getFolder(),gridFSFile.getFilename(),gridFSFile);
        }
    }


    private void downFile(String path,String fileName,GridFSFile gridFSFile){
        if(gridFSFile == null)return;
        FileOutputStream fileOutputStream = null;
        try{
            String filePath=path+File.separator+fileName;
            log.info("输出路径及文件名:{}",filePath);
            File file = new File(path+File.separator+fileName);
            if(file.exists()){
                log.info("文件{}已存在",filePath);
                file.delete();
                log.info("删除文件");
            }
            fileOutputStream = new FileOutputStream(file);
            gridFSBucket.downloadToStream(gridFSFile.getId(),fileOutputStream);

        }catch (Exception e){
            log.info("{}",Throwables.getStackTraceAsString(e));
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                }catch (Exception ex){
                    log.info("输出流关闭异常");
                    log.info("{}",Throwables.getStackTraceAsString(ex));
                }
            }
        }
    }
}
