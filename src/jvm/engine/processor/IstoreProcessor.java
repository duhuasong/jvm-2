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
 * istore_<i>: pop 顶int元素，存储到index=i的本地变量。
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
			//pop栈顶元素
			OperandVariable operVar = javaStack.popOprand();
			LocalVariable localVar = MethodUtil.convertOperand2LocalVar(operVar);
			javaStack.putVarTable(index,localVar);
		} catch (NumberFormatException e) {
			try {
				throw new JvmException("字节码["+opcode+"]的操作数不在操作码里面");
			} catch (JvmException je) {
				je.printStackTrace();
			}
		}
	}

}
