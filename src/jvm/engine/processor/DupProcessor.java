package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * pop出操作数栈中，然后把它push到操作数栈两次
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "dup")
public class DupProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//pop出操作数栈中
		OperandVariable ov = javaStack.popCurrentFrameOprandStack();
		//push到操作数栈两次
		javaStack.pushCurrentFrameOprandStack(ov);
		javaStack.pushCurrentFrameOprandStack(ov);
	}

}
