package com.weichai.test;

import com.google.common.collect.Lists;

import java.text.DecimalFormat;
import java.util.List;

public class Test2 {
	public static String myPercent(int y, int z) {
		String baifenbi = "";// 接受百分比的值
		double baiy = y * 1.0;
		double baiz = z * 1.0;
		double fen = baiy / baiz;
// NumberFormat nf = NumberFormat.getPercentInstance();//注释掉的也是一种方法
 //nf.setMinimumFractionDigits( 2 ); //保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("##0.00%");
// ##.00%
// 百分比格式，后面不足2位的用0补齐
//baifenbi=nf.format(fen);
		baifenbi = df1.format(fen);
		System.out.println(baifenbi);
		return baifenbi;
	}
	public static void main(String[] args) {
		//myPercent(2500,9000000);
		List<Integer> list = Lists.newArrayList();
		int len = list.size();
		System.out.println(len);
	}




}
