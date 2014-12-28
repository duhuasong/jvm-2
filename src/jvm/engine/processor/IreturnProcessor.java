package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
/**
 * 1��pop a int from current frame
 * 2��push it into preFrame
 * 3��discard current frame
 * @author yangrui
 *
 */
public class IreturnProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//1��popһ�� int��������current frame
		int num = (int)javaStack.popCurrentFrameOprandStack().getValue();
		//2������push��preFrame
		OperandVariable addNum = new OperandVariable(Constants.VarType.Integer_Type,num);
		javaStack.pushPreviousStackFrameOprandStack(addNum);
		//3����ǰframe�˳�JavaStack����StackFrame�е�execute������ʵ��
	}

}