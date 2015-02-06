package jvm.classloader.loader;

import jvm.memory.classinfo.ClassInfo;

/**
 * º”‘ÿ°¢—È÷§...
 * @author yangrui
 *
 */
public interface InterfaceClassLoader {
	
	public ClassInfo loadClass(String className);
}
