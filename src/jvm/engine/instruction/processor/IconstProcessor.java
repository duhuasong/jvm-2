package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
/**
 * iconst_<n>ָ��Ѳ�����n push��������ջ������5��intֵ���õ� bipush <i> ָ�
 * @author yangrui
 */
public class IconstProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		String s = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		int num = Integer.parseInt(s);
		OperandVariable operVar = new OperandVariable("Integer",num);
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

} 
