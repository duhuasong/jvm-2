package jvm.classloader.classfile;

import java.util.ArrayList;
import java.util.List;

public class CodeAttributeFile {
	
	public String attribute_name;
	
	public int attribute_length;
	
	public int max_stack;
	
	public int max_locals;
	
	public int code_length;
	
	public List<String> byteCodes = new ArrayList<String>();

	public void setByteCode(String byteCode) {
		byteCodes.add(byteCode);
	}

	public int getRemainBytes() {
		return attribute_length-2-2-4-code_length;
	}
	

}
