package jvm.memory;

import java.util.List;
import java.util.Map;

import jvm.instruct.Instruction;

public class ClassInfo {
	
	private String className;
	
	private Map<String,List<Instruction>> staticMethods;
	

	public ClassInfo(String className) {
		super();
		this.className = className;
	}

	public Map<String, List<Instruction>> getStaticMethods() {
		return staticMethods;
	}

	public void setStaticMethods(Map<String, List<Instruction>> staticMethods) {
		this.staticMethods = staticMethods;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	

}
