package com.easymail.easymail.util;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipUtils {

    private static final int  BUFFER_SIZE = 2 * 1024;

    public static void toZip(String srcDir, OutputStream out,boolean keepDirStructure){
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try{
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),keepDirStructure);
            long end = System.currentTimeMillis();
            log.info("压缩完成，耗时:{}",end-start);
        }catch (Exception e){
            log.error("{}", Throwables.getStackTraceAsString(e));
        } finally {
            if(zos != null){
                try{
                    zos.close();;
                }catch (Exception ex){
                    log.error("{}",Throwables.getStackTraceAsString(ex));
                }
            }
        }
    }

    public static void toZip(List<File> srcFiles, OutputStream out){
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try{
            zos = new ZipOutputStream(out);
            for(File srcFile:srcFiles){
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while((len = in.read(buf)) != -1){
                    zos.write(buf,0,len);
                }
                zos.closeEntry();
                in.close();
                long end = System.currentTimeMillis();
                log.info("压缩完成，耗时{}",end-start);
            }
        } catch (Exception e){
            log.error("{}",Throwables.getStackTraceAsString(e));
        } finally {
            if(zos != null){
                try {
                    zos.close();
                } catch (Exception ex){
                    log.error("{}",Throwables.getStackTraceAsString(ex));
                }
            }

        }
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while((len=in.read(buf))!=-1){
                zos.write(buf,0,len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                if(keepDirStructure){
                    //空文件夹处理
                    zos.putNextEntry(new ZipEntry(name+"/"));
                    zos.closeEntry();
                }
            } else {
             for(File file:listFiles){
                 if(keepDirStructure){
                     compress(file,zos,name+"/"+file.getName(),keepDirStructure);
                 } else {
                     compress(file,zos,file.getName(),keepDirStructure);
                 }
             }
            }
        }
    }
}
