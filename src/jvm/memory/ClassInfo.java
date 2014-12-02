package jvm.memory;

import java.util.List;

public class ClassInfo {
	
	private String className;
	
	private List<StaticMethod> staticMethods;

	public ClassInfo(String className) {
		super();
		this.className = className;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public List<StaticMethod> getStaticMethods() {
		return staticMethods;
	}


	public void setStaticMethods(List<StaticMethod> staticMethods) {
		this.staticMethods = staticMethods;
	}


	

}
