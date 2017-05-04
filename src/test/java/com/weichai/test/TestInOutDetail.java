package com.weichai.test;


import com.google.common.collect.Lists;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.DesUtil;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutGrantDao;
import com.weichai.modules.filegen.dao.InOutUsedDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailService;
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
public class TestInOutDetail {
	@Autowired
	private InOutDetailDao detailDao;

	@Autowired
	private InOutDetailService detailService;

	@Autowired
	private InOutUsedDao inOutUsedDao;


	@Test
	@Rollback(false)
	public void testBatchInsertInOutDetail(){
		List<InOutDetail> detailList = Lists.newArrayList();
		System.out.println(new Date());
		for(int i=0;i<50000;i++){
			InOutDetail detail = new InOutDetail();
			detail.setId(IdGen.uuid());
			detail.setInCode("inCode"+i);
			detail.setOutCode("outCode"+i);
			detailList.add(detail);
		}
		System.out.println(DateUtils.formatDateTime(new Date()));
		//detailDao.batchSave(detailList);
		detailService.batchInsertInOutDetails(detailList);
		System.out.println(DateUtils.formatDateTime(new Date()));
	}


	@Test
	public void testDao(){
		List<InOutDetail> detailList = Lists.newArrayList();
		System.out.println(new Date());
		for(int i=0;i<500;i++){
			InOutDetail detail = new InOutDetail();
			detail.setId(IdGen.uuid());
			detail.setInCode("inCode"+i);
			detail.setOutCode("outCode"+i);
			detailList.add(detail);
		}
		System.out.println(DateUtils.formatDateTime(new Date()));
		//detailDao.batchInsertInOutDetail(detailList);
		detailDao.batchSave(detailList);
		//detailService.batchInsertInOutDetails(detailList);
		System.out.println(DateUtils.formatDateTime(new Date()));
	}


	@Test
	public void testUsed(){
		InOutUsed inOutUsed = new InOutUsed();
		inOutUsed.setId("22");
		inOutUsed.setFilePath("uuu");
		inOutUsedDao.insertInOutUsed(inOutUsed);
	}

	@Test
	public void testGetInCode(){
		String outCode = "outCode990777";
		String inCode = "";
		System.out.println("-----start----"+ DateUtils.formatDateTime(new Date()));
		inCode = detailDao.getInCodeByOutCode(outCode);
		System.out.println(inCode);
		System.out.println("-----end----"+ DateUtils.formatDateTime(new Date()));
	}


}