package jvm.stack.operandStack;
/**
 * ²Ù×÷Êý
 * @author yangrui
 *
 */
public class OperandVariable {
	
	private String type;
	
	private Object value;
	
	public OperandVariable() {
		super();
	}


	public OperandVariable(String type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}


	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
