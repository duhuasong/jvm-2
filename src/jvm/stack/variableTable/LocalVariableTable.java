package jvm.stack.variableTable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangrui
 */
public class LocalVariableTable {
	
	private Map<Integer,LocalVariable> values = new HashMap<Integer,LocalVariable>();

	public void put(int localIndex, LocalVariable localVar) {
		values.put(localIndex, localVar);
	}

	public LocalVariable get(int index) {
		return values.get(index);
	}
	
}
