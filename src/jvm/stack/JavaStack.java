package jvm.stack;

import java.util.Stack;

public class JavaStack {
	
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private int programCounter = 0;

	
	public Stack<StackFrame> getStack() {
		return stack;
	}

	public void setStack(Stack<StackFrame> stack) {
		this.stack = stack;
	}
	
	public int getProgramCounter() {
		return programCounter;
	}

	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}

	public void execute() {
		StackFrame mainFrame = stack.get(0);
		mainFrame.execute();
	}

	
	
	

}
