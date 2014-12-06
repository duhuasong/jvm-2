package jvm.memory;

import java.util.List;

import jvm.engine.instruction.Instruction;

public class MethodInfo {
	
	private ClassInfo classInfo;
	
	private String name;
	
	private boolean isStatic;
	
	private String Scope;
	
	private ParameterDescriptor descriptor;
	
	private List<Instruction> methodInstructions;

	public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}


	public List<Instruction> getMethodInstructions() {
		return methodInstructions;
	}

	public void setMethodInstructions(List<Instruction> methodInstructions) {
		this.methodInstructions = methodInstructions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public String getScope() {
		return Scope;
	}

	public void setScope(String scope) {
		Scope = scope;
	}

	public ParameterDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(ParameterDescriptor descriptor) {
		this.descriptor = descriptor;
	}


}
