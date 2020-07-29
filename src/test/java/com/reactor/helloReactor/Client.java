package com.reactor.helloReactor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
	
	public static void main(String[] args) {
		// 拼写查找时间语句
		SimpleDateFormat tmFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = tmFormat.parse("20190828092000");//20190829100000
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// timeRange 单位为小时，则增加这么多秒
		long time = (date.getTime() / 1000) + 60 * 60 * Integer.parseInt("24");
		date.setTime(time * 1000);
		String newTime = tmFormat.format(date);
		System.out.println(newTime);
	}

}
