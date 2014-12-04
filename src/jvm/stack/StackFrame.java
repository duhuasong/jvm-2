package jvm.stack;

import java.util.List;
import java.util.Stack;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionInterpreter;
import jvm.memory.StaticMethod;
import jvm.stack.operandStack.OperandStack;
import jvm.stack.varTable.LocalVariableTable;

public class StackFrame {
	
	//�ֲ�������
	private LocalVariableTable localVariableTable;
	//������ջ
	private OperandStack operandStack = new OperandStack();
	
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


	
	

}
