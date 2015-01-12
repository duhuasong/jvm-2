package jvm.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.MethodInfo;

public class Memory {
	/**
	 * jvm执行的入口方法
	 */
	public static BlockingQueue<MethodInfo> entrancesMethods = new LinkedBlockingQueue<MethodInfo>();
	
	/**
	 * 方法区
	 * @author yangrui
	 *
	 */
	public static class MethodArea{
		
		private static Map<String,ClassInfo> classPool = new HashMap<String,ClassInfo>();
		
		public static void putClassInfo(String className,ClassInfo classinfo) {
			classPool.put(className, classinfo);
		}
		public static ClassInfo getClassInfo(String className) {
			return classPool.get(className);
		}
	}
	/**
	 * 堆区
	 * @author yangrui
	 *
	 */
	public static class HeapArea{
		
	}
	
}
