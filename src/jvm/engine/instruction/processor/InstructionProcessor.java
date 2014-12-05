package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;

public interface InstructionProcessor {

	public void execute(Instruction instruct, JavaStack javaStack) ;
}
