package jvm.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Memory {
	
	/**
	 * 内存中所有的类数据
	 */
	public static Map<String,ClassInfo> classPool = new HashMap<String,ClassInfo>();
	
	/**
	 * jvm执行的入口方法
	 */
	public static BlockingQueue<MethodInfo> entrancesMethods = new LinkedBlockingQueue<MethodInfo>();
	
}
