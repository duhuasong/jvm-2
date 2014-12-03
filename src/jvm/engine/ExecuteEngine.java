package jvm.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jvm.memory.Memory;
import jvm.memory.StaticMethod;

/**
 * 在内存中找到main方法入口，并执行
 * @author yangrui
 *
 */
public class ExecuteEngine  {
	
	public static ExecutorService pool = Executors.newCachedThreadPool();
			
	public static void execute() {
		
		while(Memory.entrancesMethods.size() > 0){
			try {
				StaticMethod mainMethod = Memory.entrancesMethods.take();
				createStackAndExecute(mainMethod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static void createStackAndExecute(StaticMethod mainMethod) {
		pool.execute(new StackWorker(mainMethod));
	}

}
