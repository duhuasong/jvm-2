package jvm.stack.operandStack;
/**
 * ������
 * @author yangrui
 *
 */
public class OperandVariable {
	
	private Object value;
	
	public OperandVariable() {
		super();
	}

	public OperandVariable(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}


}
