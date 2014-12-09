package jvm.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jvm.memory.Memory;
import jvm.memory.classinfo.MethodInfo;

/**
 * ���ڴ����ҵ�main������ڣ���ִ��
 * @author yangrui
 *
 */
public class ExecuteEngine  {
	
	public static ExecutorService pool = Executors.newCachedThreadPool();
			
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
		pool.execute(new StackWorker(mainMethod));
	}

}
