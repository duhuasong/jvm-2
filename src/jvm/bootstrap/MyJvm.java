package jvm.bootstrap;

import jvm.classloader.loader.TestClassLoader;
import jvm.engine.ExecuteEngine;
/**
 * ����Ŀ�꣺�����ֽ�����ĳЩ��Ϣ���س��Լ���class�ڴ�ģ�ͣ���ִ��
 * @author yangrui
 */
public class MyJvm {
	
	public static void java(String className) {
		
		TestClassLoader testClassLoader = new TestClassLoader();
		
		testClassLoader.loadClass(className);
		
		ExecuteEngine.execute();
		
	}

	
}
