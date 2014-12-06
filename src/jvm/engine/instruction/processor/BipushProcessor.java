package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
/**
 * 指令：bipush <n>
 * bipush 把一个8bit[-128-127]数扩展成32位的int，push进操作数栈
 * @author yangrui
 *
 */
public class BipushProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		int opcodeNum = Integer.parseInt(instruct.getOpcodeNum());
		//创建操作数
		OperandVariable operVar = new OperandVariable("Integer",opcodeNum);
		//push到栈帧的操作数栈
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

}
