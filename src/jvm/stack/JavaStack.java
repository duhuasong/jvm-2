package jvm.stack;

import java.util.Stack;

import jvm.memory.MethodInfo;
import jvm.stack.operandStack.OperandVariable;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private StackFrame currentStackFrame;
	
	private StackFrame previousStackFrame;
	
	/**
	 * ���ݷ���������Ӧ��ջ֡���Ѹ�ջ֡ѹ��javaջ��
	 * @param method
	 */
	public void createAndPushFrameByMethod(MethodInfo method) {
		previousStackFrame = currentStackFrame;
		currentStackFrame = new StackFrame(method, this);
		stack.push(currentStackFrame);
	}

	
	public void start() {
		currentStackFrame.execute();
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


	public void pushCurrentFrameOprandStack(OperandVariable num) {
		currentStackFrame.pushOprandStack(num);
	}

}
