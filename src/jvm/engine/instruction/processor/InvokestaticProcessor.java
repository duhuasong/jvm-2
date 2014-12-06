package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
/**
 * 
 * ָ�invokestatic <method-spec>
 * <method-spec>�磺java/lang/System/exit(I)V������������ɣ� a classname, a methodname and a descriptor
 * 
 * (�ο���http://cs.au.dk/~mis/dOvs/jvmspec/ref--34.html)
 * @author yangrui
 *
 */
public class InvokestaticProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
	}

}
