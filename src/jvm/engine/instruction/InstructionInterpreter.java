package jvm.engine.instruction;

import jvm.engine.instruction.instructSet.InstructionProcessor;
import jvm.stack.JavaStack;

public class InstructionInterpreter {

	public static void explain(Instruction instruct, JavaStack javaStack) {
		InstructionProcessor processor = findProcessor(instruct);
		processor.execute(instruct,javaStack);
	}

	private static InstructionProcessor findProcessor(Instruction instruct) {
		return null;
	}
	
}
