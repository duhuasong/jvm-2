package jvm.classloader.classfile;

public class ConstantFile {
	
	public String type;
	
	//class����������
	public int uft8_index;
	
	public String content;
	
	//field��method��nameAndType������ǰ���ֽ�����
	public int pre_uft8_index;
	
	//field��method��nameAndType�����ĺ����ֽ�����
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
