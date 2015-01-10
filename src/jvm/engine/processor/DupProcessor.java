package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * pop��������ջ�У�Ȼ�����push��������ջ����
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "dup")
public class DupProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//pop��������ջ��
		OperandVariable ov = javaStack.popCurrentFrameOprandStack();
		//push��������ջ����
		javaStack.pushCurrentFrameOprandStack(ov);
		javaStack.pushCurrentFrameOprandStack(ov);
	}

}
