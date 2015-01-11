package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.MethodInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokespecial")
public class InvokespecialProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		//pop出参数
		OperandVariable[] paramaters = javaStack.popOprandArray(MethodUtil.parseMethodInputSize(method_descripter));
		//pop出InstanceInfo
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popOprand().getValue();
	
		//根据方法的descripter，从classFile中找到对应的方法
		MethodInfo methodInfo = MethodUtil.searchMethod(method_descripter); 
		//把该方法、instanceInfo，push到当前帧中
		javaStack.createAndPushFrame(methodInfo,instanceInfo);
		//把之前栈帧操作数中的所有数据pop，存放在新栈帧的本地变量表的0、1、2...
		javaStack.pushOprandArray(paramaters);
		javaStack.executeFrame();
	}

}
