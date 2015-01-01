package jvm.memory.classinfo;

import java.util.List;

public class ClassInfo {
	
	private String name;
	
	private List<MethodInfo> methods;
	
	private List<FieldInfo> fields;

	public ClassInfo(String name, List<MethodInfo> methods) {
		super();
		this.setName(name);
		this.methods = methods;
	}

	public ClassInfo(String name) {
		super();
		this.setName(name);
	}
	
	public ClassInfo() {
	}

	public List<MethodInfo> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}

	public MethodInfo getMethodByName(String name) {
		for(MethodInfo method : methods){
			if(method.getName().equals(name)){
				return method;
			}
		}
		return null;
	}
	
	public MethodInfo getMethod(String methodName, String methodType) {
		for(MethodInfo method : methods){
			if(method.getName().equals(methodName) && method.getDescriptor().equals(methodType)){
				return method;
			}
		}
		return null;
		
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
		sb.append("className : ").append(name).append("\n").append("\n");
		for(MethodInfo mi : methods){
			sb.append(mi.toString()).append("\n");;
		}
		return sb.toString();
	}

	public List<FieldInfo> getFields() {
		return fields;
	}

	public void setFields(List<FieldInfo> fields) {
		this.fields = fields;
	}

	
	
	


	

}
