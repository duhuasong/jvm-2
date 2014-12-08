package jvm.classloader.classfile;

public class ConstantFile {
	
	public String type;
	
	public int uft8_index;
	
	public String content;

	public ConstantFile(String type, int uft8_index) {
		super();
		this.type = type;
		this.uft8_index = uft8_index;
	}
	
	public ConstantFile(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	
	

}
