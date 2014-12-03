package jvm.stack;

import java.util.Stack;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	public Stack<StackFrame> getStack() {
		return stack;
	}

	public void setStack(Stack<StackFrame> stack) {
		this.stack = stack;
	}

	/*public void execute() {
		StackFrame mainFrame = getCurrentStackFrame();
		mainFrame.execute();
	}*/
	
	public StackFrame getCurrentStackFrame() {
		return stack.peek();
	}

	
	
	

}
