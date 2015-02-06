package jvm.memory.classinfo;

import java.util.List;

import jvm.engine.instruction.Instruction;

public class MethodInfo {
	
	private ClassInfo classInfo;
	
	private String name;
	
	private String descriptor;
	
	private TypeDescriptor typeDescriptor;
	
	private boolean isSynchronized = false;
	
	private List<Instruction> methodInstructions;
	
	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public TypeDescriptor getTypeDescriptor() {
		return typeDescriptor;
	}

	public void setTypeDescriptor(TypeDescriptor typeDescriptor) {
		this.typeDescriptor = typeDescriptor;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("methodName : ").append(name).append("\n");
		sb.append("descriptor : ").append(descriptor).append("\n");
		for(Instruction inst : methodInstructions){
			sb.append(inst.opcode).append("  ").append(inst.opcodeNum==null?"":inst.opcodeNum).append("\n");
		}
		return sb.toString();
	}

	public boolean isSynchronized() {
		return isSynchronized;
	}

	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}


}
