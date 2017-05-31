package com.wfu.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QRCodeUtil {
	// 年标识码
	private static String[] yArr = { "q", "w", "e", "r", "t", "y", "u", "i",
			"o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x",
			"c", "v", "b", "n", "m", "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	// 月标识码
	private static String[] mArr = { "q", "w", "e", "r", "t", "y", "u", "i",
			"o", "p", "a", "s" };
	// 日标识码
	private static String[] dArr = { "0", "q", "w", "e", "r", "t", "y", "u",
			"i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z",
			"x", "c", "v", "b", "n", "m", "Q", "W", "E", "R", "T" };
	// 批次标识符
	private static String[] bArr = { "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
			"z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z" };
	// 年份初始值
	private static int initYear = 2017;

	// 获取外码前缀
	public static String getPrefixOutSideStr() {
		return "W";
	}

	// 获取内外码前缀
	public static String getPrefixInSideStr() {
		return "N";
	}

	// 获取年标识码
	public static String getYearStr() {
		Calendar calender = Calendar.getInstance();
		int year = calender.get(Calendar.YEAR);// 年
		return yArr[year - initYear];
	}

	// 获取月份标识码
	public static String getMonthStr() {
		Calendar calender = Calendar.getInstance();
		int month = calender.get(Calendar.MONTH);// 月
		return mArr[month];
	}

	// 获取日标识码
	public static String getDayStr() {
		Calendar calender = Calendar.getInstance();
		int day = calender.get(Calendar.DATE);// 日
		return dArr[day];
	}

	// 获取批次标识码
	public static String getBatchStr(int i) {
		return bArr[i];
	}

	// 获取后六位随机码
	public static String getRandomStr(int Len) {

		String[] baseString = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		Random random = new Random();
		int length = baseString.length;
		String randomString = "";
		for (int i = 0; i < length; i++) {
			randomString += baseString[random.nextInt(length)];
		}
		random = new Random(System.currentTimeMillis());
		String resultStr = "";
		for (int i = 0; i < Len; i++) {
			resultStr += randomString.charAt(random.nextInt(randomString
					.length() - 1));
		}
		return resultStr;
	}

	// 获取后10位随机码
	public static String getRandomStr10(int batch) {
		StringBuffer code = new StringBuffer("");
		code.append(getYearStr());
		code.append(getMonthStr());
		code.append(getDayStr());
		code.append(getBatchStr(batch));
		code.append(getRandomStr(6));
		return code.toString();
	}

	public static String getQRCode(String codeType, String supplierCode,int batch) {
		StringBuffer code = new StringBuffer("");
		code.append(codeType);
		code.append(supplierCode);
		code.append(getRandomStr10(batch));
		return code.toString();
	}

	// count码个数,codeType码类型(内、外码),supplierCode 供应商类型
	public static List<String> getCode(int count, String codeType,
			String supplierCode,int batch) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> codeList = new ArrayList<String>();
		while (map.size() < count) {
			String code = getQRCode(codeType, supplierCode,batch);
			String keyCode=StrToLowerCase(code);
			if (!("1".equals((String) map.get(keyCode)))) {
				map.put(keyCode, "1");
				codeList.add(code);
			}
		}
		return codeList;
	}
	//W01qrqcPxTTeN->w01qrqcpxtten
	public static String StrToLowerCase(String str) {
		StringBuffer sb = new StringBuffer();
		if (str != null){
			for (int i = 0; i < str.length(); i++){
				char c = str.charAt(i);
				if(Character.isUpperCase(c)){
					sb.append(Character.toLowerCase(c));
				}else{
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
	public static void main(String args[]) throws Exception {
		// System.out.println(yArr.length);
		// System.out.println(mArr.length);
		// System.out.println(dArr.length);
		// System.out.println(bArr.length);
		// System.out.println(getRandomStr10());
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println(myFmt.format(new Date()));
		List<String> nList = getCode(5, "N", "01",1);
		List<String> wList = getCode(5, "W", "01",1);
		StringBuffer sf = new StringBuffer("");
		sf.append("{");
		sf.append("data:[");
		for (int i = 0; i < nList.size(); i++) {
			sf.append("{");
			sf.append("id:");
			sf.append(i);
			sf.append(",");

			sf.append("W:");
			sf.append("'");
			sf.append(wList.get(i));
			sf.append("'");
			sf.append(",");

			sf.append("N:");
			sf.append("'");
			sf.append(nList.get(i));
			sf.append("'");
			sf.append("}");
			if (i != nList.size() - 1) {
				sf.append(",");
			}

		}
		sf.append("]");
		sf.append("}");
		// file(内存)----输入流---->【程序】----输出流---->file(内存)
		File file = new File("d:/temp", "addfile.txt");
		try {
			file.createNewFile(); // 创建文件
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String key = "Wc@TpSoft";
		String sftr = sf.toString();
		// byte[] encode = AESUtil.encrypt(sftr.substring(0, sftr.length()-1),
		// key);
		// String content = AESUtil.parseByte2HexStr(encode);
		// System.out.println(sftr.substring(0, sftr.length()-1));

		// 向文件写入内容(输出流)
		String str = DesUtil.encrypt(sftr,DesUtil.KEY);
		String str123=DesUtil.decrypt(str,DesUtil.KEY);
		System.out.println(sftr);
		System.out.println(str123);
		byte bt[] = new byte[1024];
		bt = str.getBytes();
		try {
			FileOutputStream in = new FileOutputStream(file);
			try {
				in.write(bt, 0, bt.length);
				in.close();
				// boolean success=true;
				// System.out.println("写入文件成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(myFmt.format(new Date()));

	}

}
