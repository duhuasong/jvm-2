package jvm.stack;

import java.util.Stack;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private StackFrame currentStackFrame;
	
	private StackFrame previousStackFrame;
	
	
	public void push(StackFrame stackFrame) {
		previousStackFrame = currentStackFrame;
		stack.push(stackFrame);
		currentStackFrame = stackFrame;
	}

	public Stack<StackFrame> getStack() {
		return stack;
	}

	public void setStack(Stack<StackFrame> stack) {
		this.stack = stack;
	}

	public StackFrame getCurrentStackFrame() {
		return currentStackFrame;
	}

	public void setCurrentStackFrame(StackFrame currentStackFrame) {
		this.currentStackFrame = currentStackFrame;
	}

	public StackFrame getPreviousStackFrame() {
		return previousStackFrame;
	}

	public void setPreviousStackFrame(StackFrame previousStackFrame) {
		this.previousStackFrame = previousStackFrame;
	}
	
	

}
