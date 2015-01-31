package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * ָ�aload_<n>
 * �ѱ��ر���<n>push��������ջ
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "aload,iload,lload")
public class AloadProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.opcode;
		String index = instruct.opcodeNum;
		if(index == null){
			index = opcode.split("\\_")[1];
		}
		javaStack.loadTableToStack(Integer.parseInt(index));
	}

}
