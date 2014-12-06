package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
/**
 * iconst_<n>ָ��ѳ���n push��������ջ������5��intֵ���õ� bipush <i> ָ�
 * @author yangrui
 */
public class IconstProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		//ָ���»��ߺ���ĳ�������
		String num_str = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		int num = Integer.parseInt(num_str);
		//����������
		OperandVariable operVar = new OperandVariable("Integer",num);
		//push��ջ֡�Ĳ�����ջ
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

} 