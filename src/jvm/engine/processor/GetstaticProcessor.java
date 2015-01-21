package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.FieldUtil;
import jvm.util.annotation.ProcessorAnnotation;


/**
 * getstatic <field-spec> <descriptor>:把类的静态变量push到操作数栈
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
		//创建操作数 TODO type有什么标准？
		OperandVariable operVar = new OperandVariable(type,fullName);
		//push到栈帧的操作数栈
		javaStack.pushOprand(operVar);
	}

}
