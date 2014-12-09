package jvm.classloader.assist;

public class ElementDescripter {
	
	public String name;
	//字节数，如果为0，则表示字节数不固定
	public int size;
	
	public ElementDescripter(String name, int size) {
		super();
		this.name = name;
		this.size = size;
	}
	
	
	
}
