/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.register.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.register.entity.ScanDetail;
import com.weichai.modules.register.entity.UserRegister;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface ScanDetailDao {

    public int insertScanDetail(ScanDetail scanDetail);

    public List<ScanDetail> selectScanDetailByCon(ScanDetail scanDetail);


}