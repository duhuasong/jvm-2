package jvm.stack;

import java.util.Stack;

import jvm.memory.classinfo.MethodInfo;
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

	
	public void executeCurFrame() {
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

	/**
	 * �ѱ��ر�������index�е�����load��������ջ
	 * @param index
	 */
	public void loadCurFrameTableToStack(int index) {
		currentStackFrame.loadTableToStack(index);
	}

	/**
	 * ��֮ǰջ֡�������е���������pop���������ջ֡�ı��ر������0��1��2...
	 */
	public void preOprandStackToCurLocalTable() {
		int size = previousStackFrame.getOprandStackSize();
		for(int i=0;i<size;i++){
			OperandVariable operVar = previousStackFrame.popOprandStack();
			LocalVariable localVar = new LocalVariable(operVar.getType(), operVar.getValue());
			currentStackFrame.putLocalVarTable(i, localVar);
		}
	}

}
