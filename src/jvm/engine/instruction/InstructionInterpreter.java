package jvm.engine.instruction;

import jvm.stack.JavaStack;
import jvm.util.exception.JvmException;
import jvm.util.factory.ProcessorFactory;
/**
 * @author yangrui
 *
 */
public class InstructionInterpreter {

	public static void explain(Instruction instruct, JavaStack javaStack) {
		InstructionProcessor processor = ProcessorFactory.createProcessor(instruct.opcode.toLowerCase());
		if(processor == null){
			try {
				throw new JvmException("["+instruct.opcode+"]没有对应的Processor");
			} catch (JvmException e) {
				e.printStackTrace();
			}
		}
		new InstructionProcessorProxy(processor).execute(instruct,javaStack);
	}
}
