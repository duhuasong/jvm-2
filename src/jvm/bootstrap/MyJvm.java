package jvm.bootstrap;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import jvm.classloader.BaseClassLoader;
import jvm.daemon.EntrancesSearcher;

public class MyJvm {
	
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public static void java(String className) {
		
		BaseClassLoader.loadClass(className);
		
		launchJvm();
	}

	private static void launchJvm() {
		
		pool.execute(new EntrancesSearcher());
		
	}
	
}
