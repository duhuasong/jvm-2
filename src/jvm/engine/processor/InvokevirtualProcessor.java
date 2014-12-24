package jvm.engine.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
import jvm.util.MethodUtil;
/**
 * getstatic  java/lang/System.out:Ljava/io/PrintStream;
   invokevirtual  java/io/PrintStream.println:(I)V
   1、根据参数描述(I)，从操作数栈中把参数pop，然后pop出方法对象
   2、在对象中搜索方法
   3、执行方法（注入参数）
   
   
   String className = "java.lang.System";
		String fieldName = "out";
		String methodName = "println";
		String paramType = "int";
		
		try {
			Class<?> classMsg = Class.forName(className);
			Field fieldMsg = classMsg.getField(fieldName);
			Object outField = fieldMsg.get(classMsg);
			Class<?> print_stream_class = fieldMsg.getType();
			Method methodMsg = print_stream_class.getMethod(methodName,new Class[]{int.class});
			methodMsg.invoke(outField,22222);
			
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
 * @author yangrui
 *
 */
public class InvokevirtualProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		
		String className = MethodUtil.parseClassName(method_descripter);
		String method_name = MethodUtil.parseMethodName(method_descripter);
		Class[] paramater_class = MethodUtil.parseMethodInputType(method_descripter);
		
		//pop出参数
		
		//pop出field_descripter
		
		//根据field_descripter得到field_name
		
		String field_name = null;
		
	}

}
