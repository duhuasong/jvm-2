package jvm.classloader;

import jvm.memory.classinfo.ClassInfo;

/**
 * ���ء���֤...
 * @author yangrui
 *
 */
public interface InterfaceClassLoader {
	
	public ClassInfo loadClass(String className);
}
