package jvm.engine.processor;

import jvm.engine.InstructionProcessor;
import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
/**
 * 指令：bipush <n>
 * bipush 把一个8bit[-128-127]数扩展成32位的int，push进操作数栈
 * @author yangrui
 *
 */
public class BipushProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		int opcodeNum = (int)instruct.getOpcodeNum();
		//创建操作数
		OperandVariable operVar = new OperandVariable(Constants.VarType.Integer_Type,opcodeNum);
		//push到栈帧的操作数栈
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

}
