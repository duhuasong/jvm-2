package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
/**
 * 1��pop a int from current frame
 * 2��push it into preFrame
 * 3��discard current frame
 * @author yangrui
 *
 */
public class ReturnProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//��ǰframe�˳�JavaStack����StackFrame�е�execute������ʵ��
	}

}
