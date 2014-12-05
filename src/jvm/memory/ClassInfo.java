package jvm.memory;

import java.util.List;

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

	


	

}
