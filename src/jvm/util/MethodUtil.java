package jvm.util;

import java.util.List;

import jvm.engine.instruction.Instruction;
import jvm.memory.Memory;
import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.MethodInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.operandStack.OperandVariable;
import jvm.stack.variableTable.LocalVariable;
import jvm.util.common.StringUtil;
import jvm.util.exception.JvmException;

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
		ClassInfo classInfo = Memory.MethodArea.getClassInfo(className);
		
		String methodName = parseMethodName(method_descripter);
		String methodType = parseMethodTypeWithPath(method_descripter);
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
		return StringUtil.replacePathToPoint(arr[0]);
	}
	/**
	 * method_descripter : java/lang/StringBuilder.<init>:(Ljava/lang/String;)V------->(Ljava.lang.String;)V
	 * @param method_descripter
	 * @return	
	 */
	public static String parseMethodTypeWithPoint(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return StringUtil.replacePathToPoint(arr[1]);
	}
	/**
	 * method_descripter : java/lang/StringBuilder.<init>:(Ljava/lang/String;)V------->(Ljava/lang/String;)V
	 * @param method_descripter
	 * @return	
	 */
	public static String parseMethodTypeWithPath(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return arr[1];
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
	public static Class<?>[] parseMethodInputType(String method_descripter) {
		Class<?>[] input_class = null;
		String methodType = parseMethodTypeWithPoint(method_descripter);
		String inputType = methodType.split("\\)")[0].substring(1);
		String[] inputTypeArray = inputType.split(";");
		if(inputTypeArray.length > 0){
			input_class = new Class[inputTypeArray.length];
			for(int i=0;i<inputTypeArray.length;i++){
				String type = inputTypeArray[i];
				if(type.equals(Constants.VarType.Integer_Type)){
					input_class[i] = int.class;
				}else if(type.equals(Constants.VarType.Long_Type)){
					input_class[i] = long.class;
				}else if(type.equals(Constants.VarType.Boolean_Type)){
					input_class[i] = boolean.class;
				}else if(type.equals(Constants.VarType.Byte_Type)){
					input_class[i] = byte.class;
				}else if(type.equals(Constants.VarType.Char_Type)){
					input_class[i] = char.class;
				}else if(type.equals(Constants.VarType.Void_Type)){
					input_class[i] = void.class;
				}else if(type.startsWith(Constants.VarType.Object_Type)){
					String className = type.substring(1);
					try {
						input_class[i] = Class.forName(className);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return input_class;
	}
	
	
	public static String toStringWithInstructionList(List<Instruction> list) {
		StringBuilder sb = new StringBuilder();
		for(Instruction ins : list){
			sb.append("\n").append(ins.toString());
		}
		return sb.toString();
	}
	/**
	 * 获取方法入参个数
	 * @param method_descripter
	 * @return
	 */
	public static int parseMethodInputSize(String method_descripter) {
		String methodType = parseMethodTypeWithPoint(method_descripter);
		String inputType = methodType.split("\\)")[0].substring(1);
		if(StringUtil.isBlank(inputType)){
			return 0;
		}
		String[] inputTypeArray = inputType.split(";");
		return inputTypeArray.length;
	}
	
	public static LocalVariable convertOperand2LocalVar(
			OperandVariable operandVariable) {
		return new LocalVariable(operandVariable.getType(),operandVariable.getValue());
	}
	/**
	 * 调用实例方法是，获取localVar
	 * 
	 * @param instanceInfo
	 * @param paramaters
	 * @return
	 */
	public static LocalVariable[] getLocalVarArray(InstanceInfo instanceInfo,
			OperandVariable[] paramaters) {
		LocalVariable[] array = new LocalVariable[paramaters.length+1];
		array[0] = new LocalVariable(Constants.VarType.Object_Type,instanceInfo);
		for(int i=1;i<array.length;i++){
			array[i] = MethodUtil.convertOperand2LocalVar(paramaters[paramaters.length-i]);
		}
		return array;
	}
	/**
	 * 是否是java的api
	 * @param method_descripter
	 * @return
	 */
	public static boolean isOfficial(String method_descripter) {
		if(method_descripter.startsWith("java")){
			return true;
		}
		return false;
	}
	public static boolean isNotObject(String superClass) {
		return !"java/lang/Object".equals(superClass);
	}
	/**
	 * 在实例中对应的子类和父类中，寻找指定的方法
	 * @param instanceInfo
	 * @param method_descripter
	 * @return
	 */
	public static MethodInfo searchMethod(InstanceInfo instanceInfo,
			String method_descripter) {
		try {
			throw new JvmException(1);
		} catch (JvmException e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
