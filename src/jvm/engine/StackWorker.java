package jvm.engine;

import jvm.memory.StaticMethod;
import jvm.stack.JavaStack;
/**
 * ���̴߳���һ��javaջ���̣߳�
 * @author yangrui
 *
 */
public class StackWorker implements Runnable{

	private JavaStack javaStack;

	public StackWorker(StaticMethod mainMethod) {
		initStack(mainMethod);
	}
	/**
	 * ��ʼ��ջ��ջ֡
	 */
	private void initStack(StaticMethod mainMethod) {
		javaStack = new JavaStack();
		javaStack.createAndPushFrameByMethod(mainMethod);
	}

	@Override
	public void run() {
		javaStack.execute();
	}

}
