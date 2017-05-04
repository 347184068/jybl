/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.validatecode.service;

import com.google.common.collect.Lists;
import com.weichai.common.persistence.Page;
import com.weichai.common.utils.DateUtils;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.validatecode.dao.InOutProductDao;
import com.weichai.modules.validatecode.entity.InOutProduct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 内外码发放Service
 * @author 张丽
 * @version 2017-03-14
 */
@Service
public class InOutProductService {
    private static final Logger LOG = Logger.getLogger(InOutProductService.class);
    @Autowired
    private InOutProductDao inOutProductDao;

    @Autowired
    private InOutDetailDao inOutDetailDao;

    public ValidateResult queryCountInCode(String qrCode){
        ValidateResult result = new ValidateResult();

         InOutProduct product = new InOutProduct();
        product.setInCode(qrCode);
         product = inOutProductDao.queryInOutProduct(product);
        if(product==null){
            result.setCount("0");
            return result;
        }
         int inNum = product.getInNum();
        int num_param = inNum;
        int inNum_after = (++num_param);
        product.setInNum(inNum_after);
        inOutProductDao.updateInNum(product);
        if(inNum>=1){
            result.setCount("3");
            result.setInOutProduct(product);
            return result;
        }else {
            result.setCount("1");
            result.setInOutProduct(product);
            return result;
        }
    }
    public ValidateResult queryCountOutCode(String qrCode){
        ValidateResult result = new ValidateResult();
        InOutProduct product = new InOutProduct();
        product.setOutCode(qrCode);
        product = inOutProductDao.queryInOutProduct(product);
        if(product==null){
            result.setCount("0");
            return result;
        }
         int outNum = product.getOutNum();
        int num_param = outNum;

        int outNum_after = (++num_param);
        product.setOutNum(outNum_after);
         inOutProductDao.updateOutNum(product);

        if(outNum>=3){
            result.setCount("3");
            result.setInOutProduct(product);
            return result;
        }
        result.setCount("1");
        result.setInOutProduct(product);
        return result;

    }

    public ValidateResult queryCountProductId(String qrCode){
        ValidateResult result = new ValidateResult();

        InOutProduct product = new InOutProduct();
        product.setProductId(qrCode);
        product = inOutProductDao.queryInOutProduct(product);
        if(product==null){
            result.setCount("0");
            return result;
        }
        int productNum = product.getProductNum();
        int num_param = productNum;
        int productNum_after = (++num_param);
        product.setProductNum(productNum_after);
        inOutProductDao.updateProductNum(product);
        if(productNum>=3){
            result.setCount("3");
            result.setInOutProduct(product);
            return result;
        }else {
            result.setCount("1");
            result.setInOutProduct(product);
            return result;
        }
    }

    public ValidateResult queryCountSecurity(String qrCode){
        ValidateResult result = new ValidateResult();

        InOutProduct product = new InOutProduct();
        product.setSecurity(qrCode);
        product = inOutProductDao.queryInOutProduct(product);
        if(product==null){
            result.setCount("0");
            return result;
        }
        int securityNum = product.getSecurityNum();
        int num_param = securityNum;
        int securityNum_after = (++num_param);
        product.setSecurityNum(securityNum_after);
        inOutProductDao.updateSecurityNum(product);
        if(securityNum>=3){
            result.setCount("3");
            result.setInOutProduct(product);
            return result;
        }else {
            result.setCount("1");
            result.setInOutProduct(product);
            return result;
        }
    }


    public ValidateResult queryCountQRCode(String qrCode,String codeFlag){
        ValidateResult result = new ValidateResult();
        if(codeFlag.equals("W")){
            result =  queryCountOutCode(qrCode);
        }
        if(codeFlag.equals("N")){
            result =  queryCountInCode(qrCode);
        }
        if(codeFlag.equals("S")){
            result = queryCountSecurity(qrCode);
        }
        if(codeFlag.equals("P")){
            result = queryCountProductId(qrCode);
        }
        return result;
    }

    public ValidateResult queryQRCodeNoUpdate(String qrCode,String codeFlag){
        ValidateResult result = new ValidateResult();
        InOutProduct product = new InOutProduct();
        if(codeFlag.equals("W")){
            product.setOutCode(qrCode);
        }
        if(codeFlag.equals("N")){
            product.setInCode(qrCode);
        }
        if(codeFlag.equals("S")){
            product.setSecurity(qrCode);
        }
        if(codeFlag.equals("P")){
            product.setProductId(qrCode);
        }
        result = queryExistBySAndP(product,codeFlag);
        return result;
    }





    public ValidateResult queryExistBySAndP(InOutProduct inOutProduct,String codeFlag){
        ValidateResult result = new ValidateResult();
        InOutProduct product = new InOutProduct();
        product = inOutProductDao.queryInOutProduct(inOutProduct);
        if(product==null){
            result.setCount("0");
            result.setFlag(false);
            return result;
        }
        if(codeFlag.equals("W")){
            int countNum = 0;
            countNum = product.getOutNum();
            if(countNum>=3){
                result.setCount("3");
            }
            result.setCount("1");
        }else if(codeFlag.equals("N")){
            int countNum = 0;
            countNum = product.getInNum();
            if(countNum>=1){
                result.setCount("3");
            }
            result.setCount("1");
        }
        result.setFlag(true);
        result.setInOutProduct(product);
        return result;
    }



    /*public void updateCodeNum(InOutProduct inOutProduct,String codeFlag){
        if(codeFlag.equals("N")){
            inOutProductDao.updateInNum(inOutProduct);
        }else if(codeFlag.equals("W")){
            inOutProductDao.updateOutNum(inOutProduct);
        }
    }*/

    public void batchAddInOutProduct(List<ImtOiScan> inOutProductList){

        int batchCount =1000;
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


