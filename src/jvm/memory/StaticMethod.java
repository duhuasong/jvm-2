package jvm.memory;

import java.util.List;

import jvm.instruction.Instruction;

public class StaticMethod {
	
	private ClassInfo classInfo;
	
	private String methodName;
	
	private List<Instruction> methodInstructions;

	public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Instruction> getMethodInstructions() {
		return methodInstructions;
	}

	public void setMethodInstructions(List<Instruction> methodInstructions) {
		this.methodInstructions = methodInstructions;
	}


}
