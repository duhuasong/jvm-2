package jvm.memory.instanceinfo;

import java.util.HashMap;
import java.util.Map;

import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.FieldInfo;

public class InstanceInfo {
	
	
	public ClassInfo classInfo;
	
	public Map<FieldInfo, Object> fieldValues = new HashMap<FieldInfo, Object>();
	
	public InstanceInfo(ClassInfo targetClassIno) {
		this.classInfo = targetClassIno;
		initFieldValues();
	}
	/**
	 * ³õÊ¼»¯×Ö¶Î
	 */
	private void initFieldValues() {
		for(FieldInfo field : classInfo.getFields()){
			fieldValues.put(field, field.getDefaultValue());
		}
	}

}
