package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.varTable.LocalVariable;
/**
 * istore_<i>: pop ��intԪ�أ��洢��index=i�ı��ر�����
 * @author yangrui
 *
 */
public class IstoreProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		String localIndex = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		//popջ��Ԫ��
		OperandVariable operVar = javaStack.popCurrentFrameOprandStack();
		LocalVariable localVar = new LocalVariable(operVar.getType(),operVar.getValue());//TODO name���ȷ����
		javaStack.putCurrentFrameLocalVarTable(Integer.parseInt(localIndex),localVar);
	}

}
