package jvm.stack;

import java.util.List;
import java.util.Stack;

import jvm.instruction.Instruction;
import jvm.instruction.InstructionInterpreter;
import jvm.memory.StaticMethod;
import jvm.stack.localVarTable.LocalVariableTable;

public class StackFrame {
	
	//�ֲ�������
	private LocalVariableTable localVariableTable;
	//������ջ
	private Stack<Object> operandStack = new Stack<Object>();
	
	private int frameCounter = 0;
	
	private StaticMethod mainMethod;

	public StackFrame(StaticMethod mainMethod) {
		
		this.mainMethod = mainMethod;
		
	}

	public void execute() {
		List<Instruction> instructions = mainMethod.getMethodInstructions();
		for(Instruction instruct : instructions){
			InstructionInterpreter.explain(instruct,this);
			frameCounter++;
		}
	}

	
	
	public LocalVariableTable getLocalVariableTable() {
		return localVariableTable;
	}

	public void setLocalVariableTable(LocalVariableTable localVariableTable) {
		this.localVariableTable = localVariableTable;
	}

	public Stack<Object> getOperandStack() {
		return operandStack;
	}

	public void setOperandStack(Stack<Object> operandStack) {
		this.operandStack = operandStack;
	}

	
	
	

}
