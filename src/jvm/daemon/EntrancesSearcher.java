package jvm.daemon;

import jvm.memory.Memory;
import jvm.memory.StaticMethod;
import jvm.stack.StackOperator;

/**
 * ���ڴ����ҵ�main������ڣ���ִ��
 * @author yangrui
 *
 */
public class EntrancesSearcher implements Runnable  {

	@Override
	public void run() {
		
		try {
			StaticMethod mainMethod = Memory.entrancesMethods.take();
			
			StackOperator stackOperator = new StackOperator(mainMethod);
			
			new Thread(new StackWorker(stackOperator)).start();;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
