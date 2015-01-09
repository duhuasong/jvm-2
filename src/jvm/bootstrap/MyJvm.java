package jvm.bootstrap;

import jvm.classloader.InterfaceClassLoader;
import jvm.classloader.impl.BaseClassLoader;
import jvm.engine.ExecuteEngine;
import jvm.util.factory.ClassLoaderFactory;
import jvm.util.factory.ProcessorFactory;
/**
 * 最终目标：根据字节码中某些信息加载成自己的class内存模型，来执行
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
