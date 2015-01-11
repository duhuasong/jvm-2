package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * iconst_<n>指令，把常数n push到操作数栈（大于5的int值会用到 bipush <i> 指令）
 * @author yangrui
 */
@ProcessorAnnotation(byteCode = "iconst_")
public class IconstProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		//指令下划线后面的常量数字
		String num_str = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		int num = Integer.parseInt(num_str);
		//创建操作数
		OperandVariable operVar = new OperandVariable(Constants.VarType.Integer_Type,num);
		//push到栈帧的操作数栈
		javaStack.pushOprand(operVar);
	}

} 
