package jvm.daemon;

import jvm.stack.StackOperator;
/**
 * ���̴߳���һ��javaջ
 * @author yangrui
 *
 */
public class StackWorker implements Runnable{
	

	private StackOperator stackOperator;

	public StackWorker(StackOperator stackOperator) {
		this.stackOperator = stackOperator;
	}

	@Override
	public void run() {
		
		stackOperator.execute();
	}

}
