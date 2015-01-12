package test.other;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
	
	public static void main(String[] args) {
		
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
	}

}
