package com.localVarTable;

import com.operandStack.OperandVariable;

public class LocalVariable extends OperandVariable{
	//������
	private String name;

	//J����long���ͣ�I����int����
	private String signature;
	
	
	public LocalVariable() {
		super();
	}

	public LocalVariable(String name, String signature,int slot,String value) {
		super(slot,value);
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
