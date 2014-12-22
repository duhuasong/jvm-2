package jvm.engine.instruction;

import jvm.engine.processor.BipushProcessor;
import jvm.engine.processor.GetstaticProcessor;
import jvm.engine.processor.IaddProcessor;
import jvm.engine.processor.IconstProcessor;
import jvm.engine.processor.IloadProcessor;
import jvm.engine.processor.InvokestaticProcessor;
import jvm.engine.processor.IreturnProcessor;
import jvm.engine.processor.IstoreProcessor;
import jvm.stack.JavaStack;
/**
 * @author yangrui
 *
 */
public class InstructionInterpreter {

	public static void explain(Instruction instruct, JavaStack javaStack) {
		InstructionProcessor processor = findProcessor(instruct);
		processor.execute(instruct,javaStack);
	}

	private static InstructionProcessor findProcessor(Instruction instruct) {
		String opcode = instruct.getOpcode().toLowerCase();
		if(opcode.startsWith("iconst_")){
			return ProcessorFactory.createProcessor(IconstProcessor.class);
		}
		if(opcode.startsWith("istore_")){
			return ProcessorFactory.createProcessor(IstoreProcessor.class);
		}
		if(opcode.startsWith("bipush")){
			return ProcessorFactory.createProcessor(BipushProcessor.class);
		}
		if(opcode.startsWith("iload_")){
			return ProcessorFactory.createProcessor(IloadProcessor.class);
		}
		if(opcode.startsWith("invokestatic")){
			return ProcessorFactory.createProcessor(InvokestaticProcessor.class);
		}
		if(opcode.startsWith("iadd")){
			return ProcessorFactory.createProcessor(IaddProcessor.class);
		}
		if(opcode.startsWith("ireturn")){
			return ProcessorFactory.createProcessor(IreturnProcessor.class);
		}
		if(opcode.startsWith("getstatic")){
			return ProcessorFactory.createProcessor(GetstaticProcessor.class);
		}
		return null;
	}
	
}
