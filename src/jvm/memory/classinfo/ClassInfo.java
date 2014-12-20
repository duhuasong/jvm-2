package jvm.memory.classinfo;

import java.util.List;

import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classfile.MethodFile;

public class ClassInfo {
	
	private String name;
	
	private List<MethodInfo> methods;

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


	

}
