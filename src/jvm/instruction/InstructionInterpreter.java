package jvm.instruction;

import jvm.instruction.inter.InstructionProcessor;
import jvm.stack.StackFrame;

public class InstructionInterpreter {

	public static void explain(Instruction instruct, StackFrame stackFrame) {
		InstructionProcessor processor = findProcessor(instruct);
		processor.execute(instruct,stackFrame);
	}

	private static InstructionProcessor findProcessor(Instruction instruct) {
		return null;
	}
	
}
