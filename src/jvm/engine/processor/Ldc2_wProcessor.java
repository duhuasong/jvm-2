package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * ldc2_w pushes a two-word constant value onto the operand stack
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "ldc2_w")
public class Ldc2_wProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String num = (String)instruct.opcodeNum;
		OperandVariable ov = new OperandVariable(Constants.VarType.Long_Type, Long.parseLong(num));
		javaStack.pushOprand(ov);
	}

}
