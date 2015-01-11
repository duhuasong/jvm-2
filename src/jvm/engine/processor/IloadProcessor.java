package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * ָ�iload_<n>
 * iload_0 : push integer in local variable 0 onto the stack
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "iload_")
public class IloadProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		//ָ���»��ߺ���ĳ�������
		String index_str = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		int index = Integer.parseInt(index_str);
		javaStack.loadTableToStack(index);
	}

}
