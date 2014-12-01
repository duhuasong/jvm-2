package jvm.stack.operandStack;
/**
 * ²Ù×÷Êý
 * @author yangrui
 *
 */
public class OperandVariable {
	
	private Object value;
	
	private int slot;
	
	
	public OperandVariable() {
		super();
	}

	public OperandVariable(int slot,Object value) {
		super();
		this.value = value;
		this.slot = slot;
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


}
