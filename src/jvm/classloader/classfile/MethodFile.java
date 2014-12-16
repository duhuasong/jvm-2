package jvm.classloader.classfile;

import java.util.ArrayList;
import java.util.List;

public class MethodFile {
	
	public String access_flags;
	
	public String name_index;
	
	public String descriptor_index;
	
	public int attributes_count;
	
	//记录当前读取到第几个属性
	public int current_attributes_pointer = 1;
	//记录当前读取属性的类型
	public String current_attributes_type = "Code";
	
	public List<CodeAttributeFile> code_attributes = new ArrayList<CodeAttributeFile>();

	public void setAttributeType(String attribute_name) {
		CodeAttributeFile attr = new CodeAttributeFile();
		attr.attribute_name = attribute_name;
		code_attributes.add(attr);
	}

	public void setAttributeLength(int attribute_length) {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		attr.attribute_length = attribute_length;
	}

	public void setAttributeMaxStack(int max_stack) {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		attr.max_stack = max_stack;
		
	}

	public void setAttributeMaxLocals(int max_locals) {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		attr.max_locals = max_locals;
	}

	public void setAttributeCodeLength(int code_length) {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		attr.code_length = code_length;
	}

	public boolean isLastByteCode() {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		if(attr.byteCodes.size() == attr.code_length - 1){
			return true;
		}
		return false;
	}

	public void setAttributeByteCode(String byteCode) {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		attr.setByteCode(byteCode);
	}
	/*
	 * 已经读完code表了，现在获得当前属性剩余的字节数，一次读完
	 */
	public int getAttributeRemainBytes() {
		CodeAttributeFile attr = code_attributes.get(code_attributes.size() - 1);
		return attr.getRemainBytes();
	}
	
	
}
