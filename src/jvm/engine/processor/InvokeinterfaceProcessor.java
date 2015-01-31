package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
import jvm.util.exception.JvmException;
/**
 * 
 * Ö¸Áî£ºinvokestatic <method-spec>
 * 
 * (²Î¿¼£ºhttp://cs.au.dk/~mis/dOvs/jvmspec/ref--34.html)
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokeinterface")
public class InvokeinterfaceProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		try {
			throw new JvmException(JvmException.METHOD_NOT_IMPLEMENT);
		} catch (JvmException e) {
			e.printStackTrace();
		}
	}
	

}
