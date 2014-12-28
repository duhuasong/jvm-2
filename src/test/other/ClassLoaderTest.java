package test.other;

import jvm.classloader.loader.BaseClassLoader;

public class ClassLoaderTest {

	public static void main(String[] args) {
		BaseClassLoader bc = new BaseClassLoader();
		bc.loadClass("test.MyTest");
	}

}
