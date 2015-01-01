package jvm.classloader.help;

public class ByteCodeDesc {
	
	public String hex;
	
	public String desc;
	//操作数的字节数
	public int index_number;
	
	public ByteCodeDesc(String hex, String desc, int index_number) {
		super();
		this.hex = hex;
		this.desc = desc;
		this.index_number = index_number;
	}

	public ByteCodeDesc(String hex, String desc) {
		super();
		this.hex = hex;
		this.desc = desc;
	}
	

}
