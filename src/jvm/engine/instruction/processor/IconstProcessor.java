package jvm.engine.instruction.processor;

import jvm.engine.instruction.Instruction;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
/**
 * iconst_<n>指令，把操作数n push到操作数栈（大于5的int值会用到 bipush <i> 指令）
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
