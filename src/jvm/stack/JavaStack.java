package jvm.stack;

import java.util.Stack;

import jvm.memory.classinfo.MethodInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.MethodUtil;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private StackFrame currentFrame;
	
	private StackFrame previousFrame;
	
	/**
	 * ����ָ������������Ӧ��ջ֡���Ѹ�ջ֡ѹ��javaջ��
	 * @param method
	 */
	public void createAndPushFrame(MethodInfo method) {
		previousFrame = currentFrame;
		currentFrame = new StackFrame(method, this);
		stack.push(currentFrame);
	}

	
	public void executeFrame() {
		currentFrame.execute();
	}
	
	
	public Stack<StackFrame> getStack() {
		return stack;
	}

	public StackFrame getCurrentFrame() {
		return currentFrame;
	}

	public StackFrame getPreviousFrame() {
		return previousFrame;
	}
	/**
	 * �Ѳ�����push����ǰջ֡�Ĳ�����ջ
	 * @param num
	 */
	public void pushOprand(OperandVariable num) {
		currentFrame.pushOprandStack(num);
	}
	public void pushOprandArray(OperandVariable[] paramaters) {
		for(OperandVariable operand : paramaters){
			currentFrame.pushOprandStack(operand);
		}
	}
	/**
	 * ��OperandVariable push����һ��ջ֡��
	 * @param addNum
	 */
	public void pushOprand2PreFrame(OperandVariable addNum) {
		previousFrame.pushOprandStack(addNum);
	}
	/**
	 * pop��ǰջ֡�Ĳ�����ջ
	 * @param num
	 */
	public OperandVariable popOprand() {
		return currentFrame.popOprandStack();
	}
	/**
	 * pop��ǰջ֡length������������ת����Object
	 * @param length
	 * @return
	 */
	public Object[] popObjectArray(int length) {
		Object[] result = new Object[length];
		for(int i=0;i<length;i++){
			result[i] = popOprand().getValue();
		}
		return result;
	}
	public OperandVariable[] popOprandArray(int length) {
		OperandVariable[] result = new OperandVariable[length];
		for(int i=0;i<length;i++){
			result[i] = popOprand();
		}
		return result;
	}

	/**
	 * �ѱ���localVar�浽��ǰջ֡���ر���localIndex��
	 * @param localIndex
	 * @param localVar
	 */
	public void putVarTable(int localIndex,
			LocalVariable localVar) {
		currentFrame.putLocalVarTable(localIndex,localVar);
	}
	/**
	 * �Ѳ������飬���ص����ر�������
	 * @param operandVariables
	 */
	public void putVarTable(LocalVariable[] vars) {
		for(int i=0;i<vars.length;i++){
			putVarTable(i, vars[i]);
		}
	}
	/**
	 * ��֮ǰջ֡�������е���������pop���������ջ֡�ı��ر������0��1��2...
	 */
	public void putPreOprand2CurVarTable() {
		int size = previousFrame.getOprandStackSize();
		for(int i=0;i<size;i++){
			OperandVariable operVar = previousFrame.popOprandStack();
			LocalVariable localVar = MethodUtil.convertOperand2LocalVar(operVar);
			currentFrame.putLocalVarTable(i, localVar);
		}
	}

	/**
	 * �ѱ��ر�������index�е�����load��������ջ
	 * @param index
	 */
	public void loadTableToStack(int index) {
		currentFrame.loadTableToStack(index);
	}

	public void discardCurFrame() {
		currentFrame = previousFrame;
		previousFrame = null;
	}


	public String getCurClassConstant(int i) {
		return currentFrame.getCurClassConstant(i);
	}

	public String getCurMethodName() {
		return currentFrame.getMethod().getClassInfo().getName()+"." + currentFrame.getMethod().getName();
	}

}
