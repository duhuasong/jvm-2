package jvm.engine.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
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
		
		String method_descripter = (String)instruct.getOpcodeNum();
		
		String methodName = MethodUtil.parseMethodName(method_descripter);
		Class<?>[] methodParamaterClass = MethodUtil.parseMethodInputType(method_descripter);
		
		//pop出参数
		Object[] methodParamaterValue = popMethodParamaters(methodParamaterClass.length,javaStack);
		//pop出field_full_Name
		String field_full_Name = (String)javaStack.popCurrentFrameOprandStack().getValue();
		//根据field_descripter得到field_name
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

	private Object[] popMethodParamaters(int length, JavaStack javaStack) {
		Object[] result = new Object[length];
		for(int i=0;i<length;i++){
			result[i] = javaStack.popCurrentFrameOprandStack().getValue();
		}
		return result;
	}

}
