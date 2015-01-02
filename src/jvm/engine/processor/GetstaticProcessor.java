package jvm.engine.processor;

import jvm.engine.InstructionProcessor;
import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.FieldUtil;


/**
 * getstatic <field-spec> <descriptor>:把类的静态变量push到操作数栈
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
		//创建操作数
		OperandVariable operVar = new OperandVariable(type,fullName);
		//push到栈帧的操作数栈
		javaStack.pushCurrentFrameOprandStack(operVar);
	}

}
