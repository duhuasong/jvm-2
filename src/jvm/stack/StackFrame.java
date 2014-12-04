package jvm.stack;

import java.util.List;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionInterpreter;
import jvm.memory.BaseMethod;
import jvm.stack.operandStack.OperandStack;
import jvm.stack.varTable.LocalVariableTable;
/**
 * StackFrameֻ����JavaStack�����Ͳ���
 * @author yangrui
 *
 */
public class StackFrame {
	
	//ջ֡��Ӧ�ķ���
	private BaseMethod method;
	//�ֲ�������
	private LocalVariableTable localVariableTable;
	//������ջ
	private OperandStack operandStack = new OperandStack();
	
	private JavaStack javaStack;
	
	private int programCounter = 0;
	
	public StackFrame(BaseMethod method,JavaStack javaStack) {
		super();
		this.javaStack = javaStack;
		this.method = method;
	}
	
	public void execute() {
		List<Instruction> instructions = method.getMethodInstructions();
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
	public JavaStack getJavaStack() {
		return javaStack;
	}
	public void setJavaStack(JavaStack javaStack) {
		this.javaStack = javaStack;
	}


	
	

}
