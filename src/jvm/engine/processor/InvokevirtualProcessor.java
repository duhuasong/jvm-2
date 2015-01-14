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
   1�����ݲ�������(I)���Ӳ�����ջ�аѲ���pop��Ȼ��pop����������
   2���ڶ�������������
   3��ִ�з�����ע�������
		
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokevirtual")
public class InvokevirtualProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		//������õ���java�Լ��ķ�����ʹ�÷���
		if(MethodUtil.isOfficial(method_descripter)){
			executeOfficial(instruct,javaStack);
		}else{
			executeCustom(instruct,javaStack);
		}
	}
	/**
	 * ����InvokespecialProcessor��ʵ��
	 * @param instruct
	 * @param javaStack
	 */
	private void executeCustom(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		//pop������
		OperandVariable[] methodParamaterValue = javaStack.popOprandArray(MethodUtil.parseMethodInputSize(method_descripter));
		//pop��instanceInfo
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popOprand().getValue();
		
		//���ݷ�����descripter����classFile���ҵ���Ӧ�ķ���
		MethodInfo methodInfo = MethodUtil.searchMethod(method_descripter); 
		javaStack.createAndPushFrame(methodInfo);
		
		LocalVariable[] localVars = MethodUtil.getLocalVarArray(instanceInfo,methodParamaterValue); 
		javaStack.putVarTable(localVars);
		javaStack.executeFrame();
	}

	private void executeOfficial(Instruction instruct, JavaStack javaStack) {
		String method_descripter = (String)instruct.getOpcodeNum();
		String methodName = MethodUtil.parseMethodName(method_descripter);
		
		//pop������
		Object[] methodParamaterValue = javaStack.popObjectArray(MethodUtil.parseMethodInputSize(method_descripter));
		//pop��field_full_Name
		String field_full_Name = (String)javaStack.popOprand().getValue();
		//����field_descripter�õ�field_name
		String fieldName = getFieldName(field_full_Name);
		String className = getClassName(field_full_Name);
		
		try {
			Class<?> classMsg = Class.forName(className);
			Field fieldMsg = classMsg.getField(fieldName);
			Object actualField = fieldMsg.get(classMsg);
			Class<?> print_stream_class = fieldMsg.getType();
			Method methodMsg = print_stream_class.getMethod(methodName,new Class[]{int.class});
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
