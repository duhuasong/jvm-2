package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.varTable.LocalVariable;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * istore_<i>: pop 顶int元素，存储到index=i的本地变量。
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "istore_")
public class IstoreProcessor implements InstructionProcessor{

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		String opcode = instruct.getOpcode();
		String localIndex = instruct.getOpcode().substring(opcode.length()-1, opcode.length());
		//pop栈顶元素
		OperandVariable operVar = javaStack.popOprand();
		LocalVariable localVar = new LocalVariable(operVar.getType(),operVar.getValue());//TODO name如何确定？
		javaStack.putLocalVarTable(Integer.parseInt(localIndex),localVar);
	}

}
