package jvm.engine.instruction.instructSet;

import jvm.engine.instruction.Instruction;
import jvm.stack.StackFrame;

public interface InstructionProcessor {

	public void execute(Instruction instruct, StackFrame stackFrame) ;
}
