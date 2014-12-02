package jvm.bootstrap;

import jvm.classloader.impl.TestClassLoader;
import jvm.daemon.EntrancesSearcher;

public class MyJvm {
	
	public static void java(String className) {
		
		TestClassLoader testClassLoader = new TestClassLoader();
		
		testClassLoader.loadClass(className);
		
		launchEntranSearcher();
	}

	private static void launchEntranSearcher() {
		
		new Thread(new EntrancesSearcher()).start();
		
	}
	
}
