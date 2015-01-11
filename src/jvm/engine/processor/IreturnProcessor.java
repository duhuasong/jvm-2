package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * 1、pop a int from current frame
 * 2、push it into preFrame
 * 3、discard current frame
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "ireturn")
public class IreturnProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//1、pop一个 int操作数从current frame
		int num = (int)javaStack.popOprand().getValue();
		//2、把它push进preFrame
		OperandVariable addNum = new OperandVariable(Constants.VarType.Integer_Type,num);
		javaStack.pushOprand2PreFrame(addNum);
		//3、当前frame退出JavaStack，在StackFrame中的execute方法中实现
	}

}
