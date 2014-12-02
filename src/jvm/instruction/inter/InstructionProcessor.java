package jvm.instruction.inter;

import jvm.instruction.Instruction;
import jvm.stack.StackFrame;

public interface InstructionProcessor {

	public void execute(Instruction instruct, StackFrame stackFrame) ;
}
