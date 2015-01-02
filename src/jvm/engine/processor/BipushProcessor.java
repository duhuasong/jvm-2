package jvm.engine.processor;

import jvm.engine.InstructionProcessor;
import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
/**
 * ָ�bipush <n>
 * bipush ��һ��8bit[-128-127]����չ��32λ��int��push��������ջ
 * @author yangrui
 *
 */
public class BipushProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		int opcodeNum = (int)instruct.getOpcodeNum();
		//����������
		OperandVariable operVar = new OperandVariable(Constants.VarType.Integer_Type,opcodeNum);
		//push��ջ֡�Ĳ�����ջ
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

}
