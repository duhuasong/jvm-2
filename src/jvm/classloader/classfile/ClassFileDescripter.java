package jvm.classloader.classfile;

import java.util.ArrayList;
import java.util.List;

public class ClassFileDescripter {
	
	public final static List<ElementDescripter> classElements = new ArrayList<ElementDescripter>();
	
	static{
		classElements.add(new ElementDescripter("magic", 4));
		classElements.add(new ElementDescripter("minor_version", 2));
		classElements.add(new ElementDescripter("major_version", 2));
		classElements.add(new ElementDescripter("constant_pool_count", 2));
		classElements.add(new ElementDescripter("constant_pool_array", 0));
		classElements.add(new ElementDescripter("access_flags", 2));
		classElements.add(new ElementDescripter("this_class", 2));
		classElements.add(new ElementDescripter("super_class", 2));
		classElements.add(new ElementDescripter("interfaces_count", 2));
		classElements.add(new ElementDescripter("interfaces_array", 0));
		classElements.add(new ElementDescripter("fields_count", 2));
		classElements.add(new ElementDescripter("fields_array", 0));
		classElements.add(new ElementDescripter("methods_count", 2));
		classElements.add(new ElementDescripter("methods_array", 0));
		classElements.add(new ElementDescripter("attributes_count", 2));
		classElements.add(new ElementDescripter("attributes_array", 0));
	}
	
	
}
