package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * istore_<i>: pop ��intԪ�أ��洢��index=i�ı��ر�����
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "istore,astore")
public class IstoreProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.opcode;
		String localIndex = instruct.opcode.substring(opcode.length()-1, opcode.length());
		int index = 0;
		try {
			index = Integer.parseInt(localIndex);
		} catch (NumberFormatException e) {
			index = Integer.parseInt(instruct.opcodeNum);
		}
		//popջ��Ԫ��
		OperandVariable operVar = javaStack.popOprand();
		LocalVariable localVar = MethodUtil.convertOperand2LocalVar(operVar);
		javaStack.putVarTable(index,localVar);
	}

}
