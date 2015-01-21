package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "lreturn")
public class LreturnProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//1、从current frame中pop一个 long操作数
		long num = (long)javaStack.popOprand().getValue();
		//2、把它push进preFrame
		OperandVariable addNum = new OperandVariable(Constants.VarType.Long_Type,num);
		javaStack.pushOprand2PreFrame(addNum);
		//3、当前frame退出JavaStack，在StackFrame中的execute方法中实现
	}

}
