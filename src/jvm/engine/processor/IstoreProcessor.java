package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * istore_<i>: pop 顶int元素，存储到index=i的本地变量。
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
		//pop栈顶元素
		OperandVariable operVar = javaStack.popOprand();
		LocalVariable localVar = MethodUtil.convertOperand2LocalVar(operVar);
		javaStack.putVarTable(index,localVar);
	}

}
