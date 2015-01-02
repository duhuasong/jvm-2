package jvm.engine.processor;

import jvm.engine.InstructionProcessor;
import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.FieldUtil;


/**
 * getstatic <field-spec> <descriptor>:����ľ�̬����push��������ջ
 * 
 * @author yangrui
 *
 */
public class GetstaticProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String method_descripter = (String)instruct.getOpcodeNum();
		String type = FieldUtil.parseFieldType(method_descripter);
		String fullName = FieldUtil.parseFieldFullName(method_descripter);
		//����������
		OperandVariable operVar = new OperandVariable(type,fullName);
		//push��ջ֡�Ĳ�����ջ
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

}
