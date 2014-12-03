package jvm.engine;

import jvm.memory.StaticMethod;
import jvm.stack.JavaStack;
import jvm.stack.StackFrame;
/**
 * 该线程创建一个java栈
 * @author yangrui
 *
 */
public class StackWorker implements Runnable{

	private JavaStack javaStack;

	public StackWorker(StaticMethod mainMethod) {
		init(mainMethod);
	}
	/**
	 * 初始化栈和栈帧
	 */
	private void init(StaticMethod mainMethod) {
		if(null == this.javaStack){
			javaStack = new JavaStack();
		}
		StackFrame mainFrame = new StackFrame(mainMethod);
		javaStack.getStack().push(mainFrame);
	}

	@Override
	public void run() {
		
	}

}
