package com.weichai.test;

import com.google.common.collect.Lists;
import com.weichai.common.utils.DesUtil;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDecry {

	public static void main(String[] args) {



	}

	public static  void test(List<String> materialList){
		int batchCount =3;
		int batchLastIndex = batchCount-1;

		for(int index = 0;index < materialList.size();){
			if(batchLastIndex > materialList.size()-1){
				List<String> list = Lists.newArrayList();
				batchLastIndex = materialList.size()-1;

				list.addAll(materialList.subList(index,batchLastIndex+1));
				//materialDao.insertMaterial(list);
				index = batchLastIndex+1;
				System.out.println(list.size());
			}else {
				List<String> list1 = Lists.newArrayList();
				//list1 = materialList.subList(index,batchLastIndex+1);
				list1.addAll(materialList.subList(index, batchLastIndex + 1));
				//materialDao.insertMaterial(list1);
				index = batchLastIndex+1;
				batchLastIndex = index+(batchCount-1);
			}
		}
	}

}
