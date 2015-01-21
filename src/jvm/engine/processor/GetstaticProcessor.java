package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.FieldUtil;
import jvm.util.annotation.ProcessorAnnotation;


/**
 * getstatic <field-spec> <descriptor>:����ľ�̬����push��������ջ
 * 
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "getstatic")
public class GetstaticProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String method_descripter = (String)instruct.opcodeNum;
		String type = FieldUtil.parseFieldTypeWithPoint(method_descripter);
		String fullName = FieldUtil.parseFieldFullName(method_descripter);
		//���������� TODO type��ʲô��׼��
		OperandVariable operVar = new OperandVariable(type,fullName);
		//push��ջ֡�Ĳ�����ջ
		javaStack.pushOprand(operVar);
	}

}
