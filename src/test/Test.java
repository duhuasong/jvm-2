package test;

import jvm.classloader.loader.BaseClassLoader;

public class Test {

	public static void main(String[] args) {
		BaseClassLoader bc = new BaseClassLoader();
		bc.loadClass("test.MyTest");
	}

}
