package jvm.stack;

import java.util.List;
import java.util.Stack;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionInterpreter;
import jvm.memory.MethodInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.varTable.LocalVariableTable;
/**
 * StackFrame只能由JavaStack创建和操作
 * @author yangrui
 *
 */
public class StackFrame {
	//栈帧对应的方法
	private MethodInfo method;
	//局部变量表
	private LocalVariableTable localVariableTable;
	//操作数栈
	private Stack<OperandVariable> operandStack = new Stack<OperandVariable>();
	
	private JavaStack javaStack;
	
	private int programCounter = 0;
	
	public StackFrame(MethodInfo method,JavaStack javaStack) {
		super();
		this.javaStack = javaStack;
		this.method = method;
	}
	
	public void execute() {
		List<Instruction> instructions = method.getMethodInstructions();
		for(Instruction instruct : instructions){
			InstructionInterpreter.explain(instruct,javaStack);
			programCounter++;
		}
	}
	
	public LocalVariableTable getLocalVariableTable() {
		return localVariableTable;
	}

	public void setLocalVariableTable(LocalVariableTable localVariableTable) {
		this.localVariableTable = localVariableTable;
	}
	public JavaStack getJavaStack() {
		return javaStack;
	}
	public void setJavaStack(JavaStack javaStack) {
		this.javaStack = javaStack;
	}

	public void pushOprandStack(OperandVariable num) {
		operandStack.push(num);
	}


}
