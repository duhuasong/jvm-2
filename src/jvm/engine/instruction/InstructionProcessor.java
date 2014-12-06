package jvm.engine.instruction;

import jvm.stack.JavaStack;

public interface InstructionProcessor {

	public void execute(Instruction instruct, JavaStack javaStack) ;
}
