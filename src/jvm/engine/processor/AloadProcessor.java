package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * 指令：aload_<n>
 * 把本地变量<n>push到操作数栈
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "aload")
public class AloadProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
	}

}
