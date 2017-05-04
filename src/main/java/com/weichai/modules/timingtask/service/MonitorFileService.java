/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.timingtask.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.weichai.common.config.Global;
import com.weichai.common.utils.DesUtil;
import com.weichai.common.utils.FileUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutDetailUsed;
import com.weichai.modules.filegen.utils.CreateFileUtil;
import com.weichai.modules.timingtask.dao.ImtOiScanDao;
import com.weichai.modules.timingtask.dao.MonitorFileDao;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.List;


/**
 * 文件监测Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class MonitorFileService {
    private static final Logger LOG = Logger.getLogger(MonitorFileService.class);
    @Autowired
    private MonitorFileDao monitorFileDao;

    public final static String KEY = "Wc@TpSof";
    private  String diskDir = Global.getConfig("disk") ; //  disk = W:\\share\\
    private String moveTo = Global.getConfig("moveTo");//upload
    private String upload = Global.getConfig("upload");
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH) + 1;
    int day = now.get(Calendar.DAY_OF_MONTH);
    String sept = File.separator;

    private String today = year + sept + month+sept+day;

    public void validateDownLoadCode(){
        String dir = diskDir+ moveTo+ sept +today;
        System.out.println(dir);
        List<String> files = FileUtils.findChildrenList(new File(dir),false);

        int realNum = monitorFileDao.countInOutDetail();
        int len = files.size();
        int dataNum = len * 50000;
        if(dataNum!=realNum){
            for(int i=0;i<len;i++){
                File file = new File(dir+sept+files.get(i));
                String strCode = CreateFileUtil.readFile(file);
                String decrypt_in_outCode="";
                try {
                    decrypt_in_outCode =  DesUtil.decrypt3(strCode, KEY);
                } catch (Exception e) {
                    LOG.error("验证下载---解密失败"+e);
                }
                List<InOutDetail> inOutDetailList = Lists.newArrayList();

                JSONObject jsStr = JSONObject.parseObject(decrypt_in_outCode); //将字符串{“id”：1}
                JSONArray jsArr=(JSONArray)jsStr.get("data");
                inOutDetailList = JSONArray.parseArray(jsArr.toJSONString(),InOutDetail.class);

                for(InOutDetail detail :inOutDetailList){
                    if(!existCode(detail)){
                        String baoCaiType = detail.getN().substring(1,3);
                        detail.setId(IdGen.uuid());
                        detail.setBaoCaiType(baoCaiType);
                        monitorFileDao.insertInOutDetail(detail);
                    }

                }
            }
        }else{
            LOG.info("申请的文件已全部插入到数据库==success");
            System.out.println("申请的文件已全部插入到数据库==success");
        }


    }

    public void validatest(){
        String dir = diskDir+ upload+ sept +today;
        System.out.println(dir);
        List<String> files = FileUtils.findChildrenList(new File(dir),false);

        int realNum = monitorFileDao.countInOutDetail();
        int len = files.size();
        int sum = 0;

            for(int i=0;i<len;i++){
                File file = new File(dir+sept+files.get(i));
                String strCode = CreateFileUtil.readFile(file);
                String decrypt_in_outCode="";
                try {
                    decrypt_in_outCode =  DesUtil.decrypt3(strCode, KEY);
                } catch (Exception e) {
                    LOG.error("验证下载---解密失败"+e);
                }
                List<InOutDetailUsed> inOutDetailList = Lists.newArrayList();

                JSONObject jsStr = JSONObject.parseObject(decrypt_in_outCode); //将字符串{“id”：1}
                JSONArray jsArr=(JSONArray)jsStr.get("data");
                inOutDetailList = JSONArray.parseArray(jsArr.toJSONString(),InOutDetailUsed.class);
                sum+=inOutDetailList.size();

                System.out.println(inOutDetailList.size());

            }

        System.out.println("*****************************************");
        System.out.println(sum);


    }
    public boolean existCode(InOutDetail detail){
        InOutDetail inOutDetail = new InOutDetail();
        inOutDetail = monitorFileDao.selectExistByCode(detail);
        if(inOutDetail!=null){
            return true;
        }else {
            return false;
        }
    }









}


