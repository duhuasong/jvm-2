package jvm.classloader.classfile;


import java.util.ArrayList;
import java.util.List;

import jvm.classloader.classfile.attribute.CodeAttributeFile;
import jvm.classloader.classfile.attribute.CommonAttributeFile;
import jvm.classloader.classfile.attribute.ConstantValueAttributeFile;
import jvm.util.Constants;

public class FieldMethodFile {
	//F代表Field，M代表Method
	public char type = 'M';
	
	public String access_flags;
	
	public String name_index;
	
	public String descriptor_index;
	
	public int attributes_count;
	
	//记录当前读取到第几个属性
	public int current_attributes_pointer = 1;
	//记录当前读取属性的类型
	public String current_attributes_type = Constants.AttributeType.code;
	
	public List<CommonAttributeFile> attributes = new ArrayList<CommonAttributeFile>();

	public void setAttributeName(String attribute_name) {
		CommonAttributeFile attr = null;
		if(current_attributes_type.equals(Constants.AttributeType.code)){
			attr = new CodeAttributeFile();
		}else if(current_attributes_type.equals(Constants.AttributeType.constantValueTyep)){
			attr = new ConstantValueAttributeFile();
		}
		System.out.println(attribute_name);
		attr.attribute_name = attribute_name;
		attributes.add(attr);
	}

	public void setAttributeLength(int attribute_length) {
		CommonAttributeFile attr = attributes.get(attributes.size() - 1);
		attr.attribute_length = attribute_length;
	}

	public void setAttributeMaxStack(int max_stack) {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		attr.max_stack = max_stack;
		
	}

	public void setAttributeMaxLocals(int max_locals) {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		attr.max_locals = max_locals;
	}

	public void setAttributeCodeLength(int code_length) {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		attr.code_length = code_length;
	}

	public boolean isLastByteCode() {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		if(attr.byteCodes.size() == attr.code_length - 1){
			return true;
		}
		return false;
	}

	public void setAttributeByteCode(String byteCode) {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		attr.setByteCode(byteCode);
	}
	/*
	 * 已经读完code表了，现在获得当前属性剩余的字节数，一次读完
	 */
	public int getAttributeRemainBytes() {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		return attr.getRemainBytes();
	}

	public boolean isByteCodeReaded() {
		CodeAttributeFile attr = (CodeAttributeFile)attributes.get(attributes.size() - 1);
		if(attr.byteCodes.size() == attr.code_length){
			return true;
		}
		return false;
	}

	public boolean hasRemainAttrs() {
		return attributes.size() !=  attributes_count;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String name = type=='M' ? "method_name" : "field_name";
		sb.append(name).append(" : ").append(name_index).append("\n");
		for(int i=0;i<attributes.size();i++){
			Object code = attributes.get(i);
			sb.append(code.toString());
		}
		return sb.toString();
		
	}
	
	
}
