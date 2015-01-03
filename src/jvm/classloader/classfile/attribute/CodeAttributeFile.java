package jvm.classloader.classfile.attribute;

import java.util.ArrayList;
import java.util.List;

public class CodeAttributeFile extends CommonAttributeFile {
	
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String code : byteCodes){
			sb.append(code.toString()).append("\n");
		}
		return sb.toString();
	}
	

}
