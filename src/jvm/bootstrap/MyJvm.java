package jvm.bootstrap;

import jvm.classloader.impl.BaseClassLoader;
import jvm.classloader.loader.InterfaceClassLoader;
import jvm.engine.ExecuteEngine;
import jvm.util.factory.ClassLoaderFactory;
import jvm.util.factory.ProcessorFactory;
/**
 * @author yangrui
 */
public class MyJvm {
	
	public static void java(String className) {
		
		init();
		
		InterfaceClassLoader classloader = ClassLoaderFactory.createClassLoader(BaseClassLoader.class);
		
		classloader.loadClass(className);
		
		ExecuteEngine.execute();
		
	}

	private static void init() {
		ProcessorFactory.init();
	}

	
}
