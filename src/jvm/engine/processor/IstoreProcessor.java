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
@ProcessorAnnotation(byteCode = "istore")
public class IstoreProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		String localIndex = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		//popջ��Ԫ��
		OperandVariable operVar = javaStack.popOprand();
		LocalVariable localVar = MethodUtil.convertOperand2LocalVar(operVar);//TODO name���ȷ����
		javaStack.putVarTable(Integer.parseInt(localIndex),localVar);
	}

}
