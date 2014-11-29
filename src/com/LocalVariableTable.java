package com;

import java.util.ArrayList;
import java.util.List;

/**
 * LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       8     0     a   J
            0       8     2     b   J
            5       3     4     r   J
 * @author yangrui
 */
public class LocalVariableTable {
	//需要的插槽slot，一个槽代表32位内存单元的，所以int需要一个slot，long需要2个slot
	private int locals;
	
	//当前插槽的所在位置
	private int currentSlot = 0;
	
	private List<LocalVariable>	values = new ArrayList<>();
	/**
	 * 
	 */
	public void setLocalVariable() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 根据插槽返回数据
	 * @param slot
	 */
	public Object getLocalVariableBySlot(int slot){
		for(LocalVariable lv : values){
			if(lv.getSlot() == slot){
				return lv.getValue();
			}
		}
		return null;
	}

	public int getLocals() {
		return locals;
	}

	public void setLocals(int locals) {
		this.locals = locals;
	}

	public List<LocalVariable> getValues() {
		return values;
	}

	public void setValues(List<LocalVariable> values) {
		this.values = values;
	}
	
	
	
	
	
}
