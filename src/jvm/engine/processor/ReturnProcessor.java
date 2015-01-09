package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * 1、pop a int from current frame
 * 2、push it into preFrame
 * 3、discard current frame
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "return")
public class ReturnProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//当前frame退出JavaStack，在StackFrame中的execute方法中实现
	}

}
