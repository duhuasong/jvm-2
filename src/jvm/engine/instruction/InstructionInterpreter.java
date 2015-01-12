package jvm.engine.instruction;

import jvm.stack.JavaStack;
import jvm.util.exception.JvmException;
import jvm.util.factory.ProcessorFactory;
/**
 * @author yangrui
 *
 */
public class InstructionInterpreter {

	public static void explain(Instruction instruct, JavaStack javaStack) throws JvmException {
		InstructionProcessor processor = ProcessorFactory.createProcessor(instruct.getOpcode().toLowerCase());
		if(processor == null){
			throw new JvmException("["+instruct.getOpcode()+"]没有对应的Processor");
		}
		new InstructionProcessorProxy(processor).execute(instruct,javaStack);
	}
}
