package jvm.engine.instruction;

import jvm.engine.instruction.processor.BipushProcessor;
import jvm.engine.instruction.processor.IconstProcessor;
import jvm.engine.instruction.processor.IloadProcessor;
import jvm.engine.instruction.processor.InvokestaticProcessor;
import jvm.engine.instruction.processor.IstoreProcessor;
import jvm.stack.JavaStack;
/**
 * iconst_1
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
			return new IconstProcessor();
		}
		if(opcode.startsWith("istore_")){
			return new IstoreProcessor();
		}
		if(opcode.startsWith("bipush")){
			return new BipushProcessor();
		}
		if(opcode.startsWith("iload_")){
			return new IloadProcessor();
		}
		if(opcode.startsWith("invokestatic")){
			return new InvokestaticProcessor();
		}
		return null;
	}
	
}
