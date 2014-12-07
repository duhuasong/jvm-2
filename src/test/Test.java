package test;

import jvm.classloader.impl.BaseClassLoader;

public class Test {

	public static void main(String[] args) {
		BaseClassLoader bc = new BaseClassLoader();
		bc.loadClass("test.MyTest");
	}

}
