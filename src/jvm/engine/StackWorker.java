package jvm.engine;

import jvm.memory.StaticMethod;
import jvm.stack.JavaStack;
/**
 * ���̴߳���һ��javaջ
 * @author yangrui
 *
 */
public class StackWorker implements Runnable{

	private JavaStack javaStack;

	public StackWorker(StaticMethod mainMethod) {
		init(mainMethod);
	}
	/**
	 * ��ʼ��ջ��ջ֡
	 */
	private void init(StaticMethod mainMethod) {
		javaStack = new JavaStack();
		javaStack.pushMethodToFrame(mainMethod);
	}

	@Override
	public void run() {
		
	}

}
