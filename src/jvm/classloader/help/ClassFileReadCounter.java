package jvm.classloader.help;

import java.util.ArrayList;
import java.util.List;

public class ClassFileReadCounter {

	public final static List<ClassElement> classElements = new ArrayList<ClassElement>();

	static {
		classElements.add(new ClassElement("magic", 4));
		classElements.add(new ClassElement("minor_version", 2));
		classElements.add(new ClassElement("major_version", 2));
		classElements.add(new ClassElement("constant_pool_count", 2));
		classElements.add(new ClassElement("constant_pool_array", 1));// 先读取类型
		classElements.add(new ClassElement("access_flags", 2));
		classElements.add(new ClassElement("this_class", 2));
		classElements.add(new ClassElement("super_class", 2));
		classElements.add(new ClassElement("interfaces_count", 2));
		classElements.add(new ClassElement("interfaces_array", 0));
		classElements.add(new ClassElement("fields_count", 2));
		classElements.add(new ClassElement("fields_array", 0));
		classElements.add(new ClassElement("methods_count", 2));
		classElements.add(new ClassElement("methods_array", 2));//先读取access_flags
	}

	public ClassFileReadCounter(int i) {
		counter = i;
	}

	public ClassFileReadCounter() {

	}

	public int counter = 0;

	public ClassElement getCurElement() {
		return classElements.get(counter++);
	}
	
	
	
	//内部类
	public static class ClassElement {
		
		public String name;
		//字节数，如果为0，则表示字节数不固定
		public int size;
		
		public ClassElement(String name, int size) {
			super();
			this.name = name;
			this.size = size;
		}
	}
	
	

}
