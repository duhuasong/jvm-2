package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
import jvm.util.exception.JvmException;
/**
 * istore_<i>: pop ��intԪ�أ��洢��index=i�ı��ر�����
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "istore,astore")
public class IstoreProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		String localIndex = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		try {
			int index = Integer.parseInt(localIndex);
			//popջ��Ԫ��
			OperandVariable operVar = javaStack.popOprand();
			LocalVariable localVar = MethodUtil.convertOperand2LocalVar(operVar);
			javaStack.putVarTable(index,localVar);
		} catch (NumberFormatException e) {
			try {
				throw new JvmException("�ֽ���["+opcode+"]�Ĳ��������ڲ���������");
			} catch (JvmException je) {
				je.printStackTrace();
			}
		}
	}

}
