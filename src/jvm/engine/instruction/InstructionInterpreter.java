package jvm.engine.instruction;

import jvm.engine.processor.BipushProcessor;
import jvm.engine.processor.DupProcessor;
import jvm.engine.processor.GetstaticProcessor;
import jvm.engine.processor.IaddProcessor;
import jvm.engine.processor.IconstProcessor;
import jvm.engine.processor.IloadProcessor;
import jvm.engine.processor.InvokestaticProcessor;
import jvm.engine.processor.InvokevirtualProcessor;
import jvm.engine.processor.IreturnProcessor;
import jvm.engine.processor.IstoreProcessor;
import jvm.engine.processor.NewProcessor;
import jvm.engine.processor.ReturnProcessor;
import jvm.stack.JavaStack;
import jvm.util.exception.JvmException;
import jvm.util.factory.ProcessorFactory;
/**
 * @author yangrui
 *
 */
public class InstructionInterpreter {

	public static void explain(Instruction instruct, JavaStack javaStack) throws JvmException {
		InstructionProcessor processor = findProcessor(instruct);
		if(processor == null){
			throw new JvmException("["+instruct.getOpcode()+"]没有对应的Processor");
		}
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
		if(opcode.startsWith("invokevirtual")){
			return ProcessorFactory.createProcessor(InvokevirtualProcessor.class);
		}
		if(opcode.startsWith("return")){
			return ProcessorFactory.createProcessor(ReturnProcessor.class);
		}
		if(opcode.startsWith("new")){
			return ProcessorFactory.createProcessor(NewProcessor.class);
		}
		if(opcode.startsWith("dup")){
			return ProcessorFactory.createProcessor(DupProcessor.class);
		}
		return null;
	}
	
}
