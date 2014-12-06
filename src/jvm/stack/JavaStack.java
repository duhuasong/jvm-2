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
	 * ����ָ������������Ӧ��ջ֡���Ѹ�ջ֡ѹ��javaջ��
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
	 * �Ѳ�����push����ǰջ֡�Ĳ�����ջ
	 * @param num
	 */
	public void pushCurrentFrameOprandStack(OperandVariable num) {
		currentStackFrame.pushOprandStack(num);
	}
	/**
	 * pop��ǰջ֡�Ĳ�����ջ
	 * @param num
	 */
	public OperandVariable popCurrentFrameOprandStack() {
		return currentStackFrame.popOprandStack();
	}

	/**
	 * �ѱ���localVar�浽��ǰջ֡���ر���localIndex��
	 * @param localIndex
	 * @param localVar
	 */
	public void putCurrentFrameLocalVarTable(int localIndex,
			LocalVariable localVar) {
		currentStackFrame.putLocalVarTable(localIndex,localVar);
	}

}
