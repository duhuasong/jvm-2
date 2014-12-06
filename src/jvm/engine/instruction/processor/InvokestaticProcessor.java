package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
/**
 * 
 * 指令：invokestatic <method-spec>
 * <method-spec>如：java/lang/System/exit(I)V，由三部分组成： a classname, a methodname and a descriptor
 * 
 * (参考：http://cs.au.dk/~mis/dOvs/jvmspec/ref--34.html)
 * @author yangrui
 *
 */
public class InvokestaticProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
	}

}
