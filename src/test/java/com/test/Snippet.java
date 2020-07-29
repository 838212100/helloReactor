package com.test;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Snippet {

	public static void main(String[] args) throws InterruptedException {
//		System.out.println(geteventLevelView(Long.valueOf(0)));
//		System.out.println(geteventLevelView(Long.valueOf(1)));
//		System.out.println(geteventLevelView(null));
		
//		String value = null;
//		geteventLevelView(null);
//		
//		StackTraceElement[] elements = null;
//		try
//		{
//			throw new Exception();
//		} catch (Exception ex)
//		{
//			System.out.println(elements = ex.getStackTrace());
//			System.out.println("------------------------------");
//			System.out.println(new Exception().getStackTrace());
//		}
		
		//System.out.println(new BigDecimal((String) "   0.40"));
		
		
		System.out.println(Long.valueOf(1));
		System.out.println(Long.valueOf(1).longValue());
		A a = new A();
		B b = new B();
		Object b1 = (Object) a;
		A a1 = (A) b1;
		System.out.println(a1);
	}
	

	public static void geteventLevelView(Object value) {
		if (value instanceof Timestamp) {
			System.out.println("noe null");
		}else {
			System.out.println("null");
		}

	}

}

class A extends C{
	
}
class B extends C{
	
}
class C{
	private String name;
	/** 
	 * 获取  bare_field_comment 
	 * @return name bare_field_comment 
	 */
	public String getName() {
		return name;
	}
	/** 
	 * 设置 bare_field_comment 
	 * @param name bare_field_comment 
	 */
	public void setName(String name) {
		this.name = name;
	}
}
