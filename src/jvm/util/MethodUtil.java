package jvm.util;

import java.util.List;

import jvm.memory.MethodInfo;

public class MethodUtil {

	public static boolean isEntryMethod(MethodInfo method) {
		if(method.getName().equals("main") && method.isStatic()){
			List<String> inputList = method.getDescriptor().getInputList();
			List<String> outputList = method.getDescriptor().getOutputList();
			boolean inputRight = inputList.size() == 1 && inputList.get(0).equals(Constants.VarType.STRING_ARRAY);
			boolean outputRight = outputList.size() == 1 && outputList.get(0).equals(Constants.VarType.VOID);
			if(inputRight && outputRight){
				return true;
			}
		}
		return false;
	}

}
