package jvm.stack.varTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangrui
 */
public class LocalVariableTable {
	
	private List<LocalVariable>	values = new ArrayList<>();

	public List<LocalVariable> getValues() {
		return values;
	}

	public void setValues(List<LocalVariable> values) {
		this.values = values;
	}
	
	
	
}
