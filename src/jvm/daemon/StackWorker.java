package jvm.daemon;

import jvm.stack.StackOperator;
/**
 * 该线程创建一个java栈
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
