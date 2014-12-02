package jvm.stack.localVarTable;

import jvm.stack.operandStack.OperandVariable;

public class LocalVariable extends OperandVariable{
	//变量名
	private String name;

	//J代表long类型，I代表int类型
	private String signature;
	
	
	public LocalVariable() {
		super();
	}

	public LocalVariable(String name, String signature,String value) {
		super(value);
		this.name = name;
		this.signature = signature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	

}
