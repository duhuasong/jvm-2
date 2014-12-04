package jvm.stack.varTable;

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
	//��Ҫ�Ĳ��slot��һ���۴���32λ�ڴ浥Ԫ�ģ�����int��Ҫһ��slot��long��Ҫ2��slot
	private int locals;
	
	//��ǰ��۵�����λ��
	private int currentSlot = 0;
	
	private List<LocalVariable>	values = new ArrayList<>();
	/**
	 * 
	 */
	public void setLocalVariable() {
		// TODO Auto-generated method stub

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