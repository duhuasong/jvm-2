package jvm.classloader.classfile;

import java.util.ArrayList;
import java.util.List;

public class MethodFile {
	
	public String access_flags;
	
	public String name_index;
	
	public String descriptor_index;
	
	public int attributes_count;
	
	public List<CodeAttributeFile> code_attributes = new ArrayList<CodeAttributeFile>();
	
	
}
