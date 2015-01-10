package jvm.stack;

import java.util.List;
import java.util.Stack;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionInterpreter;
import jvm.memory.classinfo.MethodInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.varTable.LocalVariable;
import jvm.stack.varTable.LocalVariableTable;
import jvm.util.exception.JvmException;
/**
 * StackFrameֻ����JavaStack�����Ͳ���
 * @author yangrui
 *
 */
public class StackFrame {
	//ջ֡��Ӧ�ķ���
	private MethodInfo method;
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
			try {
				InstructionInterpreter.explain(instruct,javaStack);
			} catch (JvmException e) {
				e.printStackTrace();
				return;  
			}
		//	programCounter++;
		}
		//��ǰframe�˳�javaStack
		javaStack.discardCurrentFrame();
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

	public String getCurrentClassConstant(int i) {
		try {
			return method.getClassInfo().getConstantByIndex(i);
		} catch (JvmException e) {
			e.printStackTrace();
		}
		return null;
	}


}
