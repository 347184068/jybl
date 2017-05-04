/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.validatecode.dao;



import com.weichai.common.persistence.annotation.MyBatisDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.validatecode.entity.InOutProduct;

import java.util.List;

/**
 * 内外码明细DAO接口
 * @author 张丽
 * @version 2017-03-14
 */
@MyBatisDao
public interface InOutProductDao {

    /**
     *查询内码外码次数
     */
    public InOutProduct queryCountInCode(String qrCode);
    public InOutProduct queryCountOutCode(String qrCode);

    public void updateInNum(InOutProduct inOutProduct);
    public void updateOutNum(InOutProduct inOutProduct);
    public void updateProductNum(InOutProduct inOutProduct);
    public void updateSecurityNum(InOutProduct inOutProduct);

    public void insertInOutProduct(List<ImtOiScan> inOutProductList);

    public List<InOutProduct> selectInOutProductsByCon(InOutProduct inOutProduct);

    public InOutProduct queryInOutProduct(InOutProduct inOutProduct);
}