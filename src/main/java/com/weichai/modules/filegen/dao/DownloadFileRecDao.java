
package com.weichai.modules.filegen.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.DownloadFileRec;



@MyBatisDao
public interface DownloadFileRecDao {


    /**
     *添加一条下载记录
     */
    public int insertDownloadFileRec(DownloadFileRec downloadFileRec);



}