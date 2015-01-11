package jvm.engine;

import jvm.memory.classinfo.MethodInfo;
import jvm.stack.JavaStack;
/**
 * ���̴߳���һ��javaջ���̣߳�
 * @author yangrui
 *
 */
public class StackWorker implements Runnable{

	private JavaStack javaStack;

	public StackWorker(MethodInfo mainMethod) {
		initStack(mainMethod);
	}
	/**
	 * ��ʼ��ջ��ջ֡
	 */
	private void initStack(MethodInfo mainMethod) {
		javaStack = new JavaStack();
		javaStack.createAndPushFrame(mainMethod);
	}

	@Override
	public void run() {
		javaStack.executeFrame();
	}

}
