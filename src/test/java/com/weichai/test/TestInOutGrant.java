package com.weichai.test;


import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutGrantDao;
import com.weichai.modules.filegen.entity.InOutGrant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class TestInOutGrant {
	@Autowired
	private InOutGrantDao grantDao;


	/*@Test
	public void testCountBatch(){
	 	int n=2;
		n = grantDao.countBatch();
		System.out.println(n);
	}*/

	@Test
	public void testInsertInOutGrant(){
		InOutGrant inOutGrant = new InOutGrant();
		inOutGrant.setId(IdGen.uuid());
		inOutGrant.setContent("content");
		inOutGrant.setEncodes("encodes");
		inOutGrant.setSupplierType("01");
		inOutGrant.setFilePath("-----");
		grantDao.insertInOutGrant(inOutGrant);
	}




}
