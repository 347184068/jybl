package com.weichai.modules.filegen.utils;

import com.weichai.common.utils.DesUtil;
import com.weichai.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;

/**
 * Created by tepusoft on 2017/3/13.
 */
public class CreateFileUtil {
    private static Logger logger = LoggerFactory.getLogger(CreateFileUtil.class);
    public static JsonResult createFile(String destFileName) {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        //time确定txt名
        String trueFileName=String.valueOf(System.currentTimeMillis())+".txt";
        String path = "/download/"+year+"/"+month+"/"+day+"/"+trueFileName;
        destFileName += "download\\"+year+"\\"+month+"\\"+day+"\\"+trueFileName;
        JsonResult result = new JsonResult();
        result.setFlag(false);
        File file = new File(destFileName);
        if(file.exists()) {
            logger.debug("创建单个文件 " + destFileName + "失败，目标文件已存在！");
           // System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            result.setFlag(false);
            //return false;
            return result;
        }
        if (destFileName.endsWith(File.separator)) {
            logger.debug("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            //System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            //return false;
            result.setFlag(false);
            return result;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            logger.debug("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                logger.debug("创建文件所在的目录失败!");
                result.setFlag(false);
                return result;
                //return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                logger.debug("创建单个文件" + destFileName + "成功！");
                //return true;
                result.setFlag(true);
                result.setFile(file);
                result.setPath(path);
            } else {
                logger.debug("创建单个文件" + destFileName + "失败！");
               // return false;
                result.setFlag(false);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("创建单个文件" + destFileName + "失败！" + e.getMessage());
           // System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            //return false;
            result.setFlag(false);
            return result;
        }

        return result;
    }

    public static JsonResult uploadFile(MultipartFile file,String realPath_param,String prefix_path) throws IOException {
       JsonResult result = new JsonResult();
        result.setPath("");
        String path=null;// 文件路径
        String return_path="";
        String convertPath="";
        if (file!=null) {
            String type=null;// 文件类型
            String fileName=file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:"+fileName);
            logger.info("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("TXT".equals(type.toUpperCase())) {
                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH) + 1;
                    int day = now.get(Calendar.DAY_OF_MONTH);
                    String datePath = year+"\\"+month+"\\"+day+"\\";
                    String datePath2=year+"/"+month+"/"+day+"/";

                    // 项目在容器中实际发布运行的根路径
                    String realPath=realPath_param;
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+".txt";
                    // 设置存放txt文件的路径
                     /* path=realPath+System.getProperty("file.separator")+trueFileName;*/
                    String tempPath = realPath+prefix_path+"\\"+datePath;
                    File tempFile = new File(tempPath);
                    if(!tempFile.isDirectory()){
                        tempFile.mkdirs();
                    }
                    path=realPath+prefix_path+"\\"+datePath+trueFileName;
                    convertPath = prefix_path+"\\"+datePath+trueFileName;

                    return_path = "/"+prefix_path+"/"+datePath2+trueFileName;
                    logger.info("存放txt文件的路径:"+path);
                    System.out.println("存放txt文件的路径:"+path);
                    // 转存文件到指定的路径
                    File saveFile = new File(path);
                    file.transferTo(saveFile);
                    result.setFile(saveFile);
                    System.out.println("文件成功上传到指定目录下");
                    logger.info("文件成功上传到指定目录下");
                }else {
                    //System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    logger.info("不是我们想要的文件类型,请按要求重新上传");
                    return null;
                }
            }else {
                logger.info("文件类型为空");
                System.out.println("文件类型为空");
                return null;
            }
        }else {
            logger.info("没有找到相对应的文件");
            System.out.println("没有找到相对应的文件");
            return null;
        }

        result.setPath(return_path);
        return result;

    }

    public static boolean writeFile(String encode_str,File file){
        byte bt[] = new byte[1024];
        bt = encode_str.getBytes();
        try {
            FileOutputStream in = new FileOutputStream(file);
            try {
                in.write(bt, 0, bt.length);
                in.close();
                return true;
            } catch (IOException e) {
                logger.info("writeFile---"+e);
                e.printStackTrace();
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readFile(File file){

            StringBuilder result = new StringBuilder();
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s = null;
                while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                    result.append(s);
                }
                br.close();
            }catch(Exception e){
                logger.info("readFile---"+e);
                e.printStackTrace();
            }
            return result.toString();
        }

    public static boolean createDir(String destDirName) {

        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }


    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try{
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        } else {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (!CreateFileUtil.createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
    }

    public static String moveFile(String fileName,String afterPath,String movePath){
        try {
            File bFile = new File(fileName);
            File tempFile = new File(afterPath+movePath);
            if(!tempFile.isDirectory()){
                tempFile.mkdirs();
            }
            if (bFile.renameTo(new File(afterPath+movePath+bFile.getName()))) {
                System.out.println("File is moved successful!");
                return "/moveTo/";
            } else {
                logger.info("文件移动失败");
                System.out.println("File is failed to move!");
            }
        } catch (Exception e) {
            logger.info("moveFile---"+e);
            e.printStackTrace();
        }
      return "error";
    }




    public static void main(String[] args) {
        //创建目录

        String dirName = "D:/work/temp/temp0/temp1";
        //创建文件
        String fileName = dirName + "/temp2/tempFile.txt";
        CreateFileUtil.createFile(fileName);


    }


}
