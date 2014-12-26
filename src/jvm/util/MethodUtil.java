package jvm.util;

import jvm.memory.Memory;
import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.MethodInfo;

public class MethodUtil {
	/**
	 * TODO 先只判断方面名为main
	 * @param method
	 * @return
	 */
	public static boolean isEntryMethod(MethodInfo method) {
		
		/*if(method.getName().equals("main") ){
			List<String> inputList = method.getTypeDescriptor().getInputList();
			List<String> outputList = method.getTypeDescriptor().getOutputList();
			boolean inputRight = inputList.size() == 1 && inputList.get(0).equals(Constants.VarType.STRING_ARRAY);
			boolean outputRight = outputList.size() == 1 && outputList.get(0).equals(Constants.VarType.VOID);
			if(inputRight && outputRight){
				return true;
			}
		}
		return false;*/
		
		if(method.getName().equals("main") ){
			return true;
		}
		return false;
		
	}
	/**
	 * method_descripter : test/MyTest.add:(II)I
	 * @param method_descripter
	 * @return
	 */
	public static MethodInfo searchMethod(String method_descripter) {
		String className = parseClassName(method_descripter);
		ClassInfo classInfo = Memory.classPool.get(className);
		
		String methodName = parseMethodName(method_descripter);
		String methodType = parseMethodType(method_descripter);
		MethodInfo methodInfo = classInfo.getMethod(methodName,methodType);
		return methodInfo;
	}
	/**
	 * method_descripter : test/MyTest.add:(II)I
	 * 						test/MyTest
	 * @param method_descripter
	 * @return
	 */
	public static String parseClassName(String method_descripter) {
		String[] arr = method_descripter.split("\\.");
		return StringUtil.replacePathToClass(arr[0]);
	}
	/**
	 * method_descripter : test/MyTest.add:(II)I
	 * 						(II)I
	 * @param method_descripter
	 * @return	
	 */
	public static String parseMethodType(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return StringUtil.replacePathToClass(arr[1]);
	}
	/**
	 * method_descripter : test/MyTest.add:(II)I
	 * 						
	 * @param method_descripter
	 * @return	
	 */
	public static String parseMethodName(String method_descripter) {
		String[] arr = method_descripter.split("\\.");
		return arr[1].split(":")[0];
	}
	/**
	 * method_descripter : test/MyTest.add:(II)I
	 * @param method_descripter
	 * @return
	 */
	public static Class[] parseMethodInputType(String method_descripter) {
		Class[] input_class = null;
		String methodType = parseMethodType(method_descripter);
		String inputType = methodType.split("\\)")[0].substring(1);
		if(inputType.length() > 0){
			input_class = new Class[inputType.length()];
			for(int i=0;i<inputType.length();i++){
				String type = new String(new char[]{inputType.charAt(i)});
				if(type.equals(Constants.VarType.Integer_Type)){
					input_class[i] = int.class;
				}else if(type.equals(Constants.VarType.Long_Type)){
					input_class[i] = long.class;
				}
			}
		}
		return input_class;
	}

	

}
