package jvm.classloader.classstruct;

public class ClassElement {
	
	public String name;
	//字节数，如果为0，则表示字节数不固定
	public int size;
	
	public ClassElement(String name, int size) {
		super();
		this.name = name;
		this.size = size;
	}
	
	
	
}
