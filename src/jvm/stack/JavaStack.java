package jvm.stack;

import java.util.Stack;

import jvm.memory.BaseMethod;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private StackFrame currentStackFrame;
	
	private StackFrame previousStackFrame;
	
	/**
	 * 根据方法创建对应的栈帧，把该栈帧压入java栈顶
	 * @param method
	 */
	public void createAndPushFrameByMethod(BaseMethod method) {
		previousStackFrame = currentStackFrame;
		currentStackFrame = new StackFrame(method, this);
		stack.push(currentStackFrame);
	}

	
	public void execute() {
		getCurrentStackFrame().execute();
	}
	
	
	public Stack<StackFrame> getStack() {
		return stack;
	}

	public StackFrame getCurrentStackFrame() {
		return currentStackFrame;
	}

	public StackFrame getPreviousStackFrame() {
		return previousStackFrame;
	}

}
