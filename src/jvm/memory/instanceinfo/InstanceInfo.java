package jvm.memory.instanceinfo;

import java.util.HashMap;
import java.util.Map;

import jvm.memory.classinfo.FieldInfo;

public class InstanceInfo {
	
	public String type;
	
	public Map<FieldInfo, Object> fieldValues = new HashMap<FieldInfo, Object>();
	

}
