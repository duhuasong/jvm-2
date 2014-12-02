package jvm.stack;

import jvm.memory.StaticMethod;

public class StackOperator {
	
	private JavaStack javaStack;
	
	public StackOperator(StaticMethod mainMethod) {
		init(mainMethod);
	}
	
	/**
	 * ��ʼ��ջ��ջ֡
	 */
	private void init(StaticMethod mainMethod) {
		if(null == this.javaStack){
			javaStack = new JavaStack();
		}
		StackFrame mainFrame = new StackFrame(mainMethod);
		javaStack.getStack().push(mainFrame);
	}
	
	
	public void execute() {
		javaStack.execute();
	}
	

}
