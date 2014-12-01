package jvm.stack.operandStack;

import java.util.Stack;

public class OperandStack {
	
	private Stack<OperandVariable>	values = new Stack<OperandVariable>();
	/**
	 * ���ݲ�۷�������
	 * @param slot
	 */
	public Object getOperandVariableBySlot(int slot){
		for(OperandVariable value : values){
			if(value.getSlot() == slot){
				return value.getValue();
			}
		}
		return null;
	}
	
}
