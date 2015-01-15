package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;

@ProcessorAnnotation(byteCode = "areturn")
public class AreturnProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		javaStack.pushOprand2PreFrame(javaStack.popOprand());
	}
	
}