package jvm.bootstrap;

import jvm.classloader.loader.TestClassLoader;
import jvm.engine.ExecuteEngine;
/**
 * 最终目标：根据字节码中某些信息加载成自己的class内存模型，来执行
 * @author yangrui
 */
public class MyJvm {
	
	public static void java(String className) {
		
		TestClassLoader testClassLoader = new TestClassLoader();
		
		testClassLoader.loadClass(className);
		
		ExecuteEngine.execute();
		
	}

	
}
