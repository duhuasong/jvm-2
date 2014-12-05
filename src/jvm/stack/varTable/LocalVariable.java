package jvm.stack.varTable;

import jvm.stack.operandStack.OperandVariable;

public class LocalVariable extends OperandVariable{
	
	private String name;
	
	public LocalVariable() {
		super();
	}

	public LocalVariable(String name, String type,Object value) {
		super(type,value);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
