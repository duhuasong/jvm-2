package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
/**
 * ָ�bipush <n>
 * bipush ��һ��8bit[-128-127]����չ��32λ��int��push��������ջ
 * @author yangrui
 *
 */
public class BipushProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		int opcodeNum = Integer.parseInt(instruct.getOpcodeNum());
		//����������
		OperandVariable operVar = new OperandVariable("Integer",opcodeNum);
		//push��ջ֡�Ĳ�����ջ
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

}
