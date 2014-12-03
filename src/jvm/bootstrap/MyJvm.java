package jvm.bootstrap;

import jvm.classloader.TestClassLoader;
import jvm.engine.ExecuteEngine;

public class MyJvm {
	
	public static void java(String className) {
		
		TestClassLoader testClassLoader = new TestClassLoader();
		
		testClassLoader.loadClass(className);
		
		ExecuteEngine.execute();
		
	}

	
}
