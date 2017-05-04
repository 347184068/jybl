package com.weichai.test;


import com.google.common.collect.Lists;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.common.utils.QRCodeUtil;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutDetailUsedDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.filegen.service.InOutDetailUsedService;
import com.weichai.modules.material.entity.Material;
import com.weichai.modules.material.service.MaterialService;
import com.weichai.modules.timingtask.dao.ImtOiValidCodeDao;
import com.weichai.modules.timingtask.entity.ImtOiScan;
import com.weichai.modules.timingtask.entity.ImtOiValidCode;
import com.weichai.modules.timingtask.service.ImtOiScanService;
import com.weichai.modules.timingtask.service.ImtOiValidCodeService;
import com.weichai.modules.validatecode.entity.InOutProduct;
import com.weichai.modules.validatecode.service.InOutProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class TestTimingTask {
	@Autowired
	private InOutDetailUsedService inOutDetailUsedService;

	@Autowired
	private ImtOiValidCodeService imtOiValidCodeService;

	@Autowired
	private ImtOiScanService imtOiScanService;

	@Autowired
	private MaterialService materialService;
	@Autowired
	private InOutProductService inOutProductService;


	@Autowired
	private InOutDetailUsedDao inOutDetailUsedDao;

	@Autowired
	private ImtOiValidCodeDao imtOiValidCodeDao;

	@Autowired
	private InOutProductService productService;
	@Autowired
	private InOutDetailService detailService;

	@Test
	public void testImtOiValidCode(){
		//1. 从 in_out_detail_used表中取数据
		List<ImtOiValidCode> imtOiValidCodeList = Lists.newArrayList();
		System.out.println("start get from in_out_detail_used----"+new Date());
		imtOiValidCodeList = inOutDetailUsedService.getInOutDetailUsedList();
		System.out.println("end get from in_out_detail_used----"+new Date());
		//2.取出的数据批量插入IMT_OI_VALID_CODE表中
		imtOiValidCodeService.batchAddImtOiValidCode(imtOiValidCodeList);

		System.out.println("end insert IMT_OI_VALID_CODE----"+new Date());
		//3.更新in_out_detail_used表中imt_status
		inOutDetailUsedService.batchUpdateInOutDetails(imtOiValidCodeList);
		System.out.println("end ----"+new Date());
	}


	@Test
	public void testImtOiScan(){
		//1.从 IMT_OI_SCAN表中取数据
		List<ImtOiScan> imtOiScanList = Lists.newArrayList();
		System.out.println(new Date());
		imtOiScanList = imtOiScanService.getInOutProductListUnion();
		System.out.println(new Date());
		//2. 数据存到订单表
		materialService.batchAddMaterial(imtOiScanList);
		//3.数据存到三码关联表
		//inOutProductService.batchAddInOutProduct(imtOiScanList);
		//4.更新IMT_OI_SCAN表imt_status状态
		if(imtOiScanList.size()>0){
			imtOiScanService.batchUpdateImtScanStatus(imtOiScanList);
		}

	}

	@Test
	public void testBatchAddInOutProduct(){
		System.out.println("start------"+DateUtils.formatDateTime(new Date()));
		List<String> nList = QRCodeUtil.getCode(20000, "N", "01", 1);
		List<String> wList = QRCodeUtil.getCode(20000, "W", "01",1);
		List<InOutProduct> inOutProductList = Lists.newArrayList();

		List<ImtOiScan> scanList = Lists.newArrayList();

		List<InOutDetail> detailList = Lists.newArrayList();

		for (int i = 0; i < nList.size(); i++) {
			/*InOutProduct inOutProduct = new InOutProduct();
			inOutProduct.setId(IdGen.uuid());
			inOutProduct.setInCode(nList.get(i));
			inOutProduct.setOutCode(wList.get(i));
			inOutProduct.setProductId("01");
			inOutProductList.add(inOutProduct);*/
			ImtOiScan scan = new ImtOiScan();
			scan.setId(IdGen.uuid());
			scan.setInCode(nList.get(i));
			scan.setOutCode(wList.get(i));
			scanList.add(scan);

			InOutDetail detail = new InOutDetail();
			detail.setId(IdGen.uuid());
			detail.setInCode(nList.get(i));
			detail.setOutCode(wList.get(i));
			detailList.add(detail);
		}

		System.out.println("组装完成list---"+DateUtils.formatDateTime(new Date()));
		System.out.println("start insert---"+DateUtils.formatDateTime(new Date()));
		//productService.batchAddInOutProduct(inOutProductList);
		productService.batchAddInOutProduct(scanList);
		System.out.println("end  insert ---"+DateUtils.formatDateTime(new Date()));
		detailService.batchInsertInOutDetails(detailList);
		System.out.println("end  insert22 ---"+DateUtils.formatDateTime(new Date()));
	}



	/*@Test
	public void testUpdate(){
		List<ImtOiValidCode> inOutUsedList = Lists.newArrayList();
		inOutUsedList = inOutDetailUsedDao.selectInOutDetailUsedList();

		List<ImtOiValidCode> inOutUsedList2 = Lists.newArrayList();

		inOutUsedList2 = inOutUsedList.subList(0,1000);
		System.out.println(new Date());
		inOutDetailUsedDao.batchUpdateImtStatus(inOutUsedList2);
		System.out.println(new Date());

	}*/



}