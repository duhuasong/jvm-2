package jvm.stack;

import java.util.Stack;

import jvm.memory.MethodInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.varTable.LocalVariable;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private StackFrame currentStackFrame;
	
	private StackFrame previousStackFrame;
	
	/**
	 * 根据指定方法创建对应的栈帧，把该栈帧压入java栈顶
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
	/**
	 * 把操作数push到当前栈帧的操作数栈
	 * @param num
	 */
	public void pushCurrentFrameOprandStack(OperandVariable num) {
		currentStackFrame.pushOprandStack(num);
	}
	/**
	 * pop当前栈帧的操作数栈
	 * @param num
	 */
	public OperandVariable popCurrentFrameOprandStack() {
		return currentStackFrame.popOprandStack();
	}

	/**
	 * 把变量localVar存到当前栈帧本地变量localIndex上
	 * @param localIndex
	 * @param localVar
	 */
	public void putCurrentFrameLocalVarTable(int localIndex,
			LocalVariable localVar) {
		currentStackFrame.putLocalVarTable(localIndex,localVar);
	}

}
