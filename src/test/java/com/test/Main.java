package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) throws Exception {
		

		User user = new User();
		user.setName("yang");
		user.setDate(new Date());
		// Tue Nov 05 17:11:56 CST 2019
//		System.out.println(user.toString());
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("d:/flyPig.txt")));
		oos.writeObject(user);
        System.out.println("FlyPig 对象序列化成功！");
        System.out.println(user.toString());
        oos.close();
        Thread.sleep(3000);
        System.out.println(deserializeFlyPig().toString());
	}
	
	public static User deserializeFlyPig() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("d:/flyPig.txt")));
        User user = (User) ois.readObject();
        System.out.println("FlyPig 对象反序列化成功！");
        ois.close();
        return user;
    }

}

class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private transient Date date;
	
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
	/** 
	 * 获取  bare_field_comment 
	 * @return date bare_field_comment 
	 */
	public Date getDate() {
		return (Date) date.clone();
	}
	
	/** 
	 * 设置 bare_field_comment 
	 * @param date bare_field_comment 
	 */
	public void setDate(Date date) {
		this.date = (Date) date.clone();
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", date=" + date + "]";
	}
	
}