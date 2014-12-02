package jvm.bootstrap;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import jvm.classloader.TestClassLoader;
import jvm.daemon.EntrancesSearcher;

public class MyJvm {
	
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public static void java(String className) {
		
		TestClassLoader testClassLoader = new TestClassLoader();
		
		testClassLoader.loadClass(className);
		
		launchJvm();
	}

	private static void launchJvm() {
		
		pool.execute(new EntrancesSearcher());
		
	}
	
}
