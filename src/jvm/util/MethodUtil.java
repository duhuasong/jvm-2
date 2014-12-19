package jvm.util;

import java.util.List;

import jvm.memory.classinfo.MethodInfo;

public class MethodUtil {

	public static boolean isEntryMethod(MethodInfo method) {
		if(method.getName().equals("main") ){
			List<String> inputList = method.getTypeDescriptor().getInputList();
			List<String> outputList = method.getTypeDescriptor().getOutputList();
			boolean inputRight = inputList.size() == 1 && inputList.get(0).equals(Constants.VarType.STRING_ARRAY);
			boolean outputRight = outputList.size() == 1 && outputList.get(0).equals(Constants.VarType.VOID);
			if(inputRight && outputRight){
				return true;
			}
		}
		return false;
	}

}
