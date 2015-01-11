package jvm.stack;

import java.util.Stack;

import jvm.memory.classinfo.MethodInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.varTable.LocalVariable;

public class JavaStack {
	
	private Stack<StackFrame> stack = new Stack<StackFrame>();
	
	private StackFrame currentFrame;
	
	private StackFrame previousFrame;
	
	/**
	 * 根据指定方法创建对应的栈帧，把该栈帧压入java栈顶
	 * @param method
	 */
	public void createAndPushFrame(MethodInfo method) {
		previousFrame = currentFrame;
		currentFrame = new StackFrame(method, this);
		stack.push(currentFrame);
	}
	public void createAndPushFrame(MethodInfo method,
			InstanceInfo instance) {
		previousFrame = currentFrame;
		currentFrame = new StackFrame(method,instance, this);
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
	 * 把操作数push到当前栈帧的操作数栈
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
	 * 把OperandVariable push到上一个栈帧中
	 * @param addNum
	 */
	public void pushOprand2PreFrame(OperandVariable addNum) {
		previousFrame.pushOprandStack(addNum);
	}
	/**
	 * pop当前栈帧的操作数栈
	 * @param num
	 */
	public OperandVariable popOprand() {
		return currentFrame.popOprandStack();
	}
	/**
	 * pop当前栈帧length个操作数，并转化成Object
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
	 * 把变量localVar存到当前栈帧本地变量localIndex上
	 * @param localIndex
	 * @param localVar
	 */
	public void putLocalVarTable(int localIndex,
			LocalVariable localVar) {
		currentFrame.putLocalVarTable(localIndex,localVar);
	}

	/**
	 * 把本地变量表中index中的数据load进操作数栈
	 * @param index
	 */
	public void loadTableToStack(int index) {
		currentFrame.loadTableToStack(index);
	}

	/**
	 * 把之前栈帧操作数中的所有数据pop，存放在新栈帧的本地变量表的0、1、2...
	 */
	public void preOprandStack2CurLocalTable() {
		int size = previousFrame.getOprandStackSize();
		for(int i=0;i<size;i++){
			OperandVariable operVar = previousFrame.popOprandStack();
			LocalVariable localVar = new LocalVariable(operVar.getType(), operVar.getValue());
			currentFrame.putLocalVarTable(i, localVar);
		}
	}

	


	public void discardCurFrame() {
		currentFrame = previousFrame;
		previousFrame = null;
	}


	public String getCurClassConstant(int i) {
		return currentFrame.getCurrentClassConstant(i);
	}

	
	
	
	

}
