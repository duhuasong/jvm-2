package jvm.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Memory {
	
	/**
	 * �ڴ������е�������
	 */
	public static Map<String,ClassInfo> classPool = new HashMap<String,ClassInfo>();
	
	/**
	 * jvmִ�е���ڷ���
	 */
	public static BlockingQueue entrancesMethods = new LinkedBlockingDeque<>();
	
}
