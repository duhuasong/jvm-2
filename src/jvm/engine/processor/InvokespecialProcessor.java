package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.MethodInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.Constants;
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
		javaStack.createAndPushFrame(methodInfo);
		
		LocalVariable[] localVars = getLocalVarArray(instanceInfo,paramaters); 
		javaStack.putVarTable(localVars);
		
		javaStack.executeFrame();
	}

	private LocalVariable[] getLocalVarArray(InstanceInfo instanceInfo,
			OperandVariable[] paramaters) {
		LocalVariable[] array = new LocalVariable[paramaters.length+1];
		array[0] = new LocalVariable(Constants.VarType.Object_Type,instanceInfo);
		for(int i=1;i<array.length;i++){
			array[i] = MethodUtil.convertOperand2LocalVar(paramaters[paramaters.length-i]);
		}
		return array;
	}

}
