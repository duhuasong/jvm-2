package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
/**
   u1 ldc opcode = 0x12 (18) 
   u1 index 
     把一个int、float、string的数据push到操作数栈，index为当前类的常量池索引
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "ldc")
public class LdcProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//获取当前类的常量
		String content = javaStack.getCurClassConstant(Integer.parseInt(instruct.opcodeNum));
		//把该常量push到操作数栈
		javaStack.pushOprand(new OperandVariable(Constants.VarType.Object_Type,content));
	}

}
