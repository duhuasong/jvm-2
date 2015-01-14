package jvm.stack;

import java.util.List;
import java.util.Stack;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionInterpreter;
import jvm.memory.classinfo.MethodInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.stack.variableTable.LocalVariableTable;
import jvm.util.exception.JvmException;
/**
 * StackFrameֻ����JavaStack�����Ͳ���
 * @author yangrui
 *
 */
public class StackFrame {
	//ջ֡��Ӧ�ķ���
	private MethodInfo method;
	public MethodInfo getMethod() {
		return method;
	}


	public void setMethod(MethodInfo method) {
		this.method = method;
	}

	//�ֲ�������
	//TODO ֱ����map��
	private LocalVariableTable localVariableTable = new LocalVariableTable();
	//������ջ
	private Stack<OperandVariable> operandStack = new Stack<OperandVariable>();
	
	private JavaStack javaStack;
	
	//private int programCounter = 0;
	
	public StackFrame(MethodInfo method,JavaStack javaStack) {
		super();
		this.javaStack = javaStack;
		this.method = method;
	}
	

	public void execute() {
		List<Instruction> instructions = method.getMethodInstructions();
		for(Instruction instruct : instructions){
			InstructionInterpreter.explain(instruct,javaStack);
		}
		//��ǰframe�˳�javaStack
		javaStack.exitCurFrame();
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

	public OperandVariable popOprandStack() {
		return operandStack.pop();
	}
	
	public int getOprandStackSize() {
		return operandStack.size();
	}

	public void putLocalVarTable(int localIndex, LocalVariable localVar) {
		localVariableTable.put(localIndex,localVar);
	}

	public void loadTableToStack(int index) {
		LocalVariable localVar = localVariableTable.get(index);
		OperandVariable operandVar = new OperandVariable(localVar.getType(),localVar.getValue());
		operandStack.push(operandVar);
	}

	public String getCurClassConstant(int i) {
		try {
			return method.getClassInfo().getConstantByIndex(i);
		} catch (JvmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
