package jvm.engine;

import jvm.memory.MethodInfo;
import jvm.stack.JavaStack;
/**
 * 该线程创建一个java栈（线程）
 * @author yangrui
 *
 */
public class StackWorker implements Runnable{

	private JavaStack javaStack;

	public StackWorker(MethodInfo mainMethod) {
		initStack(mainMethod);
	}
	/**
	 * 初始化栈和栈帧
	 */
	private void initStack(MethodInfo mainMethod) {
		javaStack = new JavaStack();
		javaStack.createAndPushFrameByMethod(mainMethod);
	}

	@Override
	public void run() {
		javaStack.executeCurFrame();
	}

}
