package com.weichai.test;


import com.google.common.collect.Lists;
import com.weichai.common.utils.DateUtils;
import com.weichai.common.utils.IdGen;
import com.weichai.modules.filegen.dao.InOutDetailDao;
import com.weichai.modules.filegen.dao.InOutUsedDao;
import com.weichai.modules.filegen.entity.InOutDetail;
import com.weichai.modules.filegen.entity.InOutUsed;
import com.weichai.modules.filegen.service.InOutDetailService;
import com.weichai.modules.timingtask.service.MonitorFileService;
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
public class TestMonitorFile {
	@Autowired
	private MonitorFileService service;


	@Test
	public void testGetInCode(){
		 service.validatest();
	}


}