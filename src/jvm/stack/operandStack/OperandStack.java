package jvm.stack.operandStack;

import java.util.Stack;

public class OperandStack {
	
	private Stack<OperandVariable>	values = new Stack<OperandVariable>();
	/**
	 * 根据插槽返回数据
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
