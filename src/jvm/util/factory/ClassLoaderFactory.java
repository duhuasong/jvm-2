package jvm.util.factory;

import java.util.HashMap;
import java.util.Map;

import jvm.classloader.loader.InterfaceClassLoader;

public class ClassLoaderFactory {
	
	private static Map<Class<?>,InterfaceClassLoader> map = new HashMap<Class<?>,InterfaceClassLoader>();
	/**
	 * @param processorClass
	 * @return
	 */
	public static InterfaceClassLoader createClassLoader(
			Class<?> classLoaderClass) {
		InterfaceClassLoader classLoader = map.get(classLoaderClass);
		if(classLoader == null){
			try {
				classLoader =  (InterfaceClassLoader) classLoaderClass.newInstance();
				map.put(classLoaderClass, classLoader);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return classLoader;
	}
	
}
