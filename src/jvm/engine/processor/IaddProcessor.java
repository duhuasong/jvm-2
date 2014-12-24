package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
/**
 * 1、pop操作数栈中的两个数(int类型)
 * 2、相加后push进操作数栈
 * @author yangrui
 *
 */
public class IaddProcessor  implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//1、pop操作数栈中的两个数
		OperandVariable op1 = javaStack.popCurrentFrameOprandStack();
		OperandVariable op2 = javaStack.popCurrentFrameOprandStack();
		int n1 = (int)op1.getValue();
		int n2 = (int)op2.getValue();
		int total = n1 + n2;
		//2、相加后push进操作数栈
		OperandVariable addNum = new OperandVariable(Constants.VarType.Integer_Type,total);
		javaStack.pushCurrentFrameOprandStack(addNum);
	}

}
