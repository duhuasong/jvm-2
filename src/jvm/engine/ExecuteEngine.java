package jvm.engine;

import jvm.memory.Memory;
import jvm.memory.classinfo.MethodInfo;

/**
 * 在内存中找到main方法入口，并执行
 * @author yangrui
 *
 */
public class ExecuteEngine  {
	
	//public static ExecutorService pool = Executors.newCachedThreadPool();
			
	public static void execute() {
		
		while(Memory.entrancesMethods.size() > 0){
			try {
				MethodInfo mainMethod = Memory.entrancesMethods.take();
				createStackAndExecute(mainMethod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static void createStackAndExecute(MethodInfo mainMethod) {
		new Thread(new StackWorker(mainMethod)).start();
		//pool.execute();
	}

}
