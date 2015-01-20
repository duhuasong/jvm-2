package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
import jvm.util.exception.JvmException;

@ProcessorAnnotation(byteCode = "ldc2_w")
public class Ldc2_wProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		try {
			throw new JvmException(1);
		} catch (JvmException e) {
			e.printStackTrace();
		}
	}

}
