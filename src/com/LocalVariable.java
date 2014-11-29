package com;

public class LocalVariable {
	//变量名
	private String name;

	private Object value;
	
	private int slot;
	
	//J代表long类型，I代表int类型
	private String Signature;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}
	
}
