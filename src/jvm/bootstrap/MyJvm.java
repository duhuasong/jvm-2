package jvm.bootstrap;

import jvm.classloader.impl.BaseClassLoader;
import jvm.engine.ExecuteEngine;
/**
 * ����Ŀ�꣺�����ֽ�����ĳЩ��Ϣ���س��Լ���class�ڴ�ģ�ͣ���ִ��
 * @author yangrui
 */
public class MyJvm {
	
	public static void java(String className) {
		
		BaseClassLoader classloader = new BaseClassLoader();
		
		classloader.loadClass(className);
		
		ExecuteEngine.execute();
		
	}

	
}
