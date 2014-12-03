package jvm.stack;

import java.util.List;
import java.util.Stack;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionInterpreter;
import jvm.memory.StaticMethod;
import jvm.stack.localVarTable.LocalVariableTable;

public class StackFrame {
	
	//局部变量表
	private LocalVariableTable localVariableTable;
	//操作数栈
	private Stack<Object> operandStack = new Stack<Object>();
	
	private int programCounter = 0;
	
	private StaticMethod mainMethod;

	public StackFrame(StaticMethod mainMethod) {
		
		this.mainMethod = mainMethod;
		
	}

	public void execute() {
		List<Instruction> instructions = mainMethod.getMethodInstructions();
		for(Instruction instruct : instructions){
			InstructionInterpreter.explain(instruct,this);
			programCounter++;
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
