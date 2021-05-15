/**
* Copyright (C) 2018-2020
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制
*/
package co.yixiang;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import co.yixiang.modules.system.domain.Dept;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：LionCity
 * @date ：Created in 2020-03-24 16:45
 * @description：
 * @modified By：
 * @version:
 */
public class FileTest {
    /**
     * 读取excel表格内容返回List<Map>
     * @param inputStream  excel文件流
     * @param head         表头数组
     * @param headerAlias  表头别名数组
     * @return
     */
    public static List<Map<String,Object>> importExcel(InputStream inputStream, String[] head, String[] headerAlias){
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Object> header=reader.readRow(1);
        //替换表头关键字
        if(ArrayUtils.isEmpty(head)||ArrayUtils.isEmpty(headerAlias)||head.length!=headerAlias.length){
            return null;
        }else{
            for(int i=0;i<head.length;i++){
                if(head[i].equals(header.get(i))){
                    reader.addHeaderAlias(head[i],headerAlias[i]);
                }else{
                    return null;
                }

            }
        }
        //读取指点行开始的表数据（以下介绍的三个参数也可以使用动态传入，根据个人业务情况修改）
        //1：表头所在行数  2：数据开始读取位置   Integer.MAX_VALUE:数据读取结束行位置
        List<Map<String,Object>> read = reader.read(1,2,Integer.MAX_VALUE);
        return read;
    }
    /**
     * 读取excel表格内容返回List<Bean>
     * @param inputStream  excel文件流
     * @param head         表头数组
     * @param headerAlias  表头别名数组
     * @return
     */
    public static <T>List<T> importExcel(InputStream inputStream, String[] head, String[] headerAlias, Class<T> bean){
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Object> header=reader.readRow(1);
        //替换表头关键字
        if(ArrayUtils.isEmpty(head)||ArrayUtils.isEmpty(headerAlias)||head.length!=headerAlias.length){
            return null;
        }else{
            for(int i=0;i<head.length;i++){
                if(head[i].equals(header.get(i))){
                    reader.addHeaderAlias(head[i],headerAlias[i]);
                }else{
                    return null;
                }

            }
        }
        //读取指点行开始的表数据（从0开始）
        List<T> read = reader.read(1,2,bean);
        return read;
    }


    public static void main(String[] args) throws IOException {
//        readfile("D:/upload");
        ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");
        List<List<Object>> readAll = reader.read(2,reader.getRowCount());
        for (Map<String, Object> stringObjectMap : reader.readAll()) {

        }

        HashMap<Object, Object> hashMap = new HashMap<>();
        List<Map<String,Object>> readAl = reader.readAll();
//        List<Dept> readAll = reader.readAll(Dept.class);
        System.out.println(readAll);
    }
    public static void readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

                File targetFile = new File(file.getPath().replace("upload","uploadZip"));
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                ImgUtil.scale(file,targetFile,getAccuracy(file.length()/ 1024));
            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath="
                                + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());
                        File targetFile = new File(readfile.getPath().replace("upload","uploadZip"));
                        System.out.println("path2=" + targetFile.getPath());
                        System.out.println("fileSize=" + targetFile.length());
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        ImgUtil.scale(readfile,targetFile,getAccuracy(file.length()/ 1024));
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
    }

    public static BufferedImage inputImage(MultipartFile file) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = (FileInputStream) file.getInputStream();
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("读取图片文件出错！" + e.getMessage());
        }
        return srcImage;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    public static float getAccuracy(long size) {
        float accuracy;
        if (size < 400) {
            accuracy = 0.85f;
        } else if (size < 900) {
            accuracy = 0.75f;
        } else if (size < 2047) {
            accuracy = 0.6f;
        } else if (size < 3275) {
            accuracy = 0.44f;
        } else {
            accuracy = 0.4f;
        }
        return accuracy;
    }

}
