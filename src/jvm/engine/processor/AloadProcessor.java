package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.annotation.ProcessorAnnotation;
import jvm.util.exception.JvmException;
/**
 * ָ�aload_<n>
 * �ѱ��ر���<n>push��������ջ
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "aload")
public class AloadProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		if(instruct.getOpcodeNum()!=null){
			try {
				throw new JvmException("TODO [aload n]������");
			} catch (JvmException e) {
				e.printStackTrace();
			}
		}
		String index = opcode.split("\\_")[1];
		javaStack.loadTableToStack(Integer.parseInt(index));
		
	}

}
