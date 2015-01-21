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
		//1����current frame��popһ�� long������
		long num = (long)javaStack.popOprand().getValue();
		//2������push��preFrame
		OperandVariable addNum = new OperandVariable(Constants.VarType.Long_Type,num);
		javaStack.pushOprand2PreFrame(addNum);
		//3����ǰframe�˳�JavaStack����StackFrame�е�execute������ʵ��
	}

}
