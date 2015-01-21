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
     ��һ��int��float��string������push��������ջ��indexΪ��ǰ��ĳ���������
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "ldc")
public class LdcProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//��ȡ��ǰ��ĳ���
		String content = javaStack.getCurClassConstant(Integer.parseInt(instruct.opcodeNum));
		//�Ѹó���push��������ջ
		javaStack.pushOprand(new OperandVariable(Constants.VarType.Object_Type,content));
	}

}
