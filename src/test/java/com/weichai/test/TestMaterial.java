package com.weichai.test;


import com.google.common.collect.Lists;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutUsedDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.material.dao.MaterialDao;
import com.weichai.modules.material.entity.Material;
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
public class TestMaterial {

	@Autowired
	private MaterialDao materialDao;



	@Test
	public void testGetInCode(){
		String outCode = "W01qqz27rhCWr";
		String security= "111122233311223001";
		String productId = "11111111111112222222165";

		Material material = new Material();
		Material material1 = new Material();
		//material1.setOutCode(outCode);
		//material.setSecurity(security);
		material1.setProductId(productId);
		material = materialDao.selectMaterial(material1);
		System.out.println("-----end----"+ DateUtils.formatDateTime(new Date()));
	}


}