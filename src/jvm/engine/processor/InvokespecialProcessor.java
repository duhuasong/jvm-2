package jvm.engine.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
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
		
		String methodName = MethodUtil.parseMethodName(method_descripter);
		Class<?>[] methodParamaterClass = MethodUtil.parseMethodInputType(method_descripter);
		
		//pop出参数
		Object[] methodParamaterValue = popMethodParamaters(methodParamaterClass.length,javaStack);
		//pop出InstanceInfo
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popCurrentFrameOprandStack().getValue();
		
		
		
			
	}


	private Object[] popMethodParamaters(int length, JavaStack javaStack) {
		Object[] result = new Object[length];
		for(int i=0;i<length;i++){
			result[i] = javaStack.popCurrentFrameOprandStack().getValue();
		}
		return result;
	}

}
