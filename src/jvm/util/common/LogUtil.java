package jvm.util.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogUtil {
	
	public static String filepath = System.getProperty("java.class.path")+"/jvm/log.properties";
	
	public static void main(String[] args) {
		LogUtil.println("print.classfile", "222222");
		println("print.classinfo", "1111111");
	}

	public static void println(String keys,String msg) {
		boolean isOut = isOut(keys);
		if(isOut){
			System.out.println(msg);
		}
	}

	private static boolean isOut(String keys) {
		Properties prop = readfile();
		for (Object key : prop.keySet()) {
			if(key.equals(keys) && ((String)prop.get(key)).equals("true")){
				return true;
			}
		}
		return false;
	}

	private static Properties readfile() {
		InputStream is = null;
		Properties prop = null;
		try {
			is = new FileInputStream(filepath);
			prop = new Properties();
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
