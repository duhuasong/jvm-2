package jvm.memory.instanceinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.FieldInfo;
import jvm.util.FieldUtil;

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
	public FieldInfo getFieldKey(String field_descriptor) {
		String name = FieldUtil.parseFieldName(field_descriptor);
		String type = FieldUtil.parseFieldTypeWithPath(field_descriptor);
		Set<FieldInfo> set = fieldValues.keySet();
		for(FieldInfo fieldInfo : set){
			if(name.equals(fieldInfo.getName()) && type.equals(fieldInfo.getDescriptor())){
				return fieldInfo;
			}
		}
		return null;
	}
	public void putFieldValue(FieldInfo key, Object value) {
		fieldValues.put(key, value);
	}

}
