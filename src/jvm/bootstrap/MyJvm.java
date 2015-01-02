package jvm.bootstrap;

import jvm.classloader.InterfaceClassLoader;
import jvm.classloader.impl.BaseClassLoader;
import jvm.engine.ExecuteEngine;
import jvm.util.factory.ClassLoaderFactory;
/**
 * ����Ŀ�꣺�����ֽ�����ĳЩ��Ϣ���س��Լ���class�ڴ�ģ�ͣ���ִ��
 * @author yangrui
 */
public class MyJvm {
	
	public static void java(String className) {
		
		InterfaceClassLoader classloader = ClassLoaderFactory.createClassLoader(BaseClassLoader.class);
		
		classloader.loadClass(className);
		
		ExecuteEngine.execute();
		
	}

	
}
