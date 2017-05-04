package com.weichai.test;


import com.google.common.collect.Lists;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.QRCodeUtil;
import com.weichai.modules.filegen.dao.InOutGrantDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutGrant;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.validatecode.dao.InOutProductDao;
import com.weichai.modules.validatecode.entity.InOutProduct;
import com.weichai.modules.validatecode.service.InOutProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class TestInOutProduct {
	@Autowired
	private InOutProductDao productDao;

	@Autowired
	private InOutProductService productService;

	@Autowired
	private InOutDetailService detailService;

	@Test
	public void testValidateCodeDao(){
		String qrCode = "n1234rtyew334";
		int inNum = productDao.queryCountInCode(qrCode).getInNum();
		qrCode="wert543gtyjksA";
		int outNum = productDao.queryCountOutCode(qrCode).getOutNum();
		System.out.println(inNum+"--------"+outNum);
	}

	@Test
	public void testValidateCodeService(){
		Boolean flag = false;
		String qrCode = "N01qek2NxORB3";
		String codeFlag = "n";
		System.out.println(DateUtils.formatDateTime(new Date()));
		//flag = productService.queryCountQRCode(qrCode,codeFlag);
		System.out.println("-------"+flag+"--------");
		System.out.println(DateUtils.formatDateTime(new Date()));
	}


	@Test
	@Rollback(false)
	public void testBatchInsertInOutDetail(){
		List<InOutDetail> detailList = Lists.newArrayList();
		System.out.println(new Date());
		for(int i=0;i<1000000;i++){
			InOutDetail detail = new InOutDetail();
			detail.setId(IdGen.uuid());
			detail.setInCode("incode"+i);
			detail.setOutCode("outCode"+i);
			detailList.add(detail);
		}
		//System.out.println(DateUtils.formatDateTime(new Date()));
		//detailDao.batchSave(detailList);
		detailService.batchInsertInOutDetails(detailList);
		//System.out.println(DateUtils.formatDateTime(new Date()));
	}

	@Test
	public void testBatchAddInOutProduct(){
		System.out.println("start------"+DateUtils.formatDateTime(new Date()));
		List<String> nList = QRCodeUtil.getCode(50000, "N", "01", 1);
		List<String> wList = QRCodeUtil.getCode(50000, "W", "01",1);
		List<ImtOiScan> inOutProductList = Lists.newArrayList();


		List<InOutDetail> detailList = Lists.newArrayList();

		for (int i = 0; i < nList.size(); i++) {
			ImtOiScan inOutProduct = new ImtOiScan();
			inOutProduct.setId(IdGen.uuid());
			inOutProduct.setInCode(nList.get(i));
			inOutProduct.setOutCode(wList.get(i));
			inOutProduct.setProductId("311111111111122222"+i);
			inOutProduct.setBaoCaiType("01");
			inOutProductList.add(inOutProduct);


		}

		System.out.println("组装完成list---"+DateUtils.formatDateTime(new Date()));
		System.out.println("start insert---"+DateUtils.formatDateTime(new Date()));
		productService.batchAddInOutProduct(inOutProductList);
	//	System.out.println("end  insert ---"+DateUtils.formatDateTime(new Date()));
	//	detailService.batchInsertInOutDetails(detailList);
		System.out.println("end  insert22 ---"+DateUtils.formatDateTime(new Date()));
	}


}
