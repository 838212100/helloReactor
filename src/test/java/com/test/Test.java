package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {
	
	public static void main(String[] args) {
		Properties prop = initProperties("application.properties");
		
		System.out.println(prop.getProperty("spring.data.mongodb.uri"));
	}

	
	public static Properties initProperties(String fileName) {
	    Properties props = new Properties();
	    InputStream in = null;
	    try {
	    	in = Test.class.getClassLoader().getResourceAsStream(fileName);
	    	props.load(in);
	      
	    	return props;
	    }
	    catch (Exception e) {
	      return null;
	    }
	    finally {
	      if (in != null) {
	        try {
	          in.close();
	        }
	        catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	  }
}
