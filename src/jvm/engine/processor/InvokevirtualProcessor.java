package jvm.engine.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.MethodInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * getstatic  java/lang/System.out:Ljava/io/PrintStream;
   invokevirtual  java/io/PrintStream.println:(I)V
   1、根据参数描述(I)，从操作数栈中把参数pop，然后pop出方法对象
   2、在对象中搜索方法
   3、执行方法（注入参数）
		
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokevirtual")
public class InvokevirtualProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.opcodeNum;
		//如果调用的是java自己的方法，使用反射
		if(MethodUtil.isOfficial(method_descripter)){
			executeOfficial(instruct,javaStack);
		}else{
			executeCustom(instruct,javaStack);
		}
	}
	/**
	 * 类似InvokespecialProcessor的实现
	 * @param instruct
	 * @param javaStack
	 */
	private void executeCustom(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.opcodeNum;
		//pop出参数
		OperandVariable[] methodParamaterValue = javaStack.popOprandArray(MethodUtil.parseMethodInputSize(method_descripter));
		//pop出instanceInfo
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popOprand().getValue();
		
		//根据方法的descripter，从classFile中找到对应的方法
		//MethodInfo methodInfo = MethodUtil.searchMethod(method_descripter); 
		MethodInfo methodInfo = MethodUtil.searchMethod(instanceInfo,method_descripter); 
		javaStack.createAndPushFrame(methodInfo);
		
		LocalVariable[] localVars = MethodUtil.getLocalVarArray(instanceInfo,methodParamaterValue); 
		javaStack.putVarTable(localVars);
		javaStack.executeFrame();
	}

	private void executeOfficial(Instruction instruct, JavaStack javaStack) {
		String method_descripter = (String)instruct.opcodeNum;
		String methodName = MethodUtil.parseMethodName(method_descripter);
		Class<?>[] paramaterClass = MethodUtil.parseMethodInputType(method_descripter);
		//pop出参数
		Object[] methodParamaterValue = javaStack.popObjectArray(paramaterClass.length);
		//pop出field_full_Name
		String field_full_Name = (String)javaStack.popOprand().getValue();
		//根据field_descripter得到field_name
		String fieldName = getFieldName(field_full_Name);
		String className = getClassName(field_full_Name);
		
		try {
			Class<?> classMsg = Class.forName(className);
			Field fieldMsg = classMsg.getField(fieldName);
			Object actualField = fieldMsg.get(classMsg);
			Class<?> print_stream_class = fieldMsg.getType();
			Method methodMsg = print_stream_class.getMethod(methodName,paramaterClass);
			methodMsg.invoke(actualField,methodParamaterValue);
			
		} catch (ClassNotFoundException | NoSuchFieldException
				| SecurityException | IllegalArgumentException
				| IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}


	private String getClassName(String field_full_Name) {
		String[] arr = field_full_Name.split("\\.");
		return field_full_Name.substring(0, field_full_Name.length() - arr[arr.length-1].length() - 1);
	}

	private String getFieldName(String fullFieldName) {
		String[] arr = fullFieldName.split("\\.");
		return arr[arr.length-1];
	}


}
