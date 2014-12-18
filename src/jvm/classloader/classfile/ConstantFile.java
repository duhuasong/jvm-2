package jvm.classloader.classfile;
/**
 * nameAndType如果描述的是Method：方法名和入出参数
 * nameAndType如果描述的是Field：字段名和类型
 * @author yangrui
 *
 */
public class ConstantFile {
	
	public String type;
	
	//class常量的索引
	public int uft8_index;
	
	public String content;
	
	//field、method、nameAndType常量的前两字节索引
	public int pre_uft8_index;
	
	//field、method、nameAndType常量的后两字节索引
	public int last_uft8_index;

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

	public ConstantFile(String type, int class_uft8_index,
			int nameAndType_uft8_index) {
		super();
		this.type = type;
		this.pre_uft8_index = class_uft8_index;
		this.last_uft8_index = nameAndType_uft8_index;
	}
	
	

}
