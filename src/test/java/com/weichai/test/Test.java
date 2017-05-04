package com.weichai.test;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.weichai.common.utils.DateUtils;
import com.weichai.modules.filegen.entity.InOutDetailUsed;
import com.weichai.modules.timingtask.entity.ImtOiScan;


import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {

		/*List<String> materialList = Lists.newArrayList();
	 	for(int i=0;i<3;i++){
			materialList.add("o"+i);
		}
		test(materialList);
*/
		/*String qrCode = "222eeeee";
		Pattern pt = Pattern.compile("^\\d{8}$");;
		Matcher mt = pt.matcher(qrCode);
		System.out.println(mt.matches());*/
		while(true){
			Scanner sc = new Scanner(System.in);

			//String account = sc.nextLine();
			String password=sc.nextLine();
			Pattern pt_account = Pattern.compile("^[a-zA-Z0-9]{6,18}$");
			Pattern pt_pwd = Pattern.compile("^(W|N)[0-9a-zA-Z]{12}$");
			//Matcher matcher_account = pt_account.matcher(account);
			Matcher matcher_pwd = pt_pwd.matcher(password);

			//System.out.println(matcher_account.matches());
			System.out.println(matcher_pwd.matches());
		}


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
