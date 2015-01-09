package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * ָ�bipush <n>
 * bipush ��һ��8bit[-128-127]����չ��32λ��int��push��������ջ
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "bipush")
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
