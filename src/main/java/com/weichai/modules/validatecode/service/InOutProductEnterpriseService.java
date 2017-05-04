/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.validatecode.service;

import com.google.common.collect.Lists;
import com.weichai.common.persistence.Page;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.validatecode.dao.InOutProductDao;
import com.weichai.modules.validatecode.entity.InOutProduct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class InOutProductEnterpriseService {
    private static final Logger LOG = Logger.getLogger(InOutProductEnterpriseService.class);
    @Autowired
    private InOutProductDao inOutProductDao;

    @Autowired
    private InOutDetailDao inOutDetailDao;

    public ValidateResult queryCountInCode(String qrCode){
        ValidateResult result = new ValidateResult();

         InOutProduct product = new InOutProduct();
         product = inOutProductDao.queryCountInCode(qrCode);
        if(product==null){
            result.setFlag(false);
            return result;
        }else{
            result.setInOutProduct(product);
            result.setFlag(true);
            return result;
        }
    }
    public ValidateResult queryCountOutCode(String qrCode){
        ValidateResult result = new ValidateResult();
        InOutProduct product = new InOutProduct();
        product = inOutProductDao.queryCountOutCode(qrCode);
        if(product==null){
            result.setFlag(false);
            return result;
        }else{
            result.setFlag(true);
            result.setInOutProduct(product);
            return result;
        }
    }


    public ValidateResult queryCountQRCode(String qrCode,String codeFlag){
        ValidateResult result = new ValidateResult();
        InOutProduct product = new InOutProduct();

        if(codeFlag.equals("W")){
            product.setOutCode(qrCode);
            result =  queryExistBySAndP(product);
        }
        if(codeFlag.equals("N")){
            product.setInCode(qrCode);
            result =  queryExistBySAndP(product);
        }
        if(codeFlag.equals("S")){
            product.setSecurity(qrCode);
            result = queryExistBySAndP(product);
        }
        if(codeFlag.equals("P")){
            product.setProductId(qrCode);
            result = queryExistBySAndP(product);
        }
        return result;
    }

    public ValidateResult queryExistBySAndP(InOutProduct inOutProduct){
        ValidateResult result = new ValidateResult();
        InOutProduct product = new InOutProduct();
        product = inOutProductDao.queryInOutProduct(inOutProduct);
        if(product==null){
            result.setFlag(false);
            return result;
        }
        result.setFlag(true);
        result.setInOutProduct(product);
        return result;
    }


    public void batchAddInOutProduct(List<ImtOiScan> inOutProductList){

        System.out.println("-------start---"+new Date());

        int batchCount =500;
        int batchLastIndex = batchCount-1;

        for(int index = 0;index < inOutProductList.size();){
            if(batchLastIndex > inOutProductList.size()-1){
                List<ImtOiScan> list = Lists.newArrayList();
                batchLastIndex = inOutProductList.size()-1;
                list.addAll(inOutProductList.subList(index,batchLastIndex+1));
                index = batchLastIndex+1;
                inOutProductDao.insertInOutProduct(list);
            }else {
                List<ImtOiScan> list1 = Lists.newArrayList();
                list1.addAll(inOutProductList.subList(index, batchLastIndex + 1));
                inOutProductDao.insertInOutProduct(list1);
                index = batchLastIndex+1;
                batchLastIndex = index+(batchCount-1);
            }
        }
    }

    public Page<InOutProduct> findPage(Page<InOutProduct> page, InOutProduct inOutProduct){
        inOutProduct.setPage(page);
        page.setList(inOutProductDao.selectInOutProductsByCon(inOutProduct));
        return page;
    }




}


