package jvm.classloader.help;
/**
 * 读取class文件用到的一些辅助变量
 * @author yangrui
 *
 */
public class TempVariable {
	public int constant_pool_count = 0;
	public int constant_pool_pointer = 1;
	//当前读取的常量类型
	public String constant_type = null;
	//当前读取的常量类型对应的片段（utf8对应3，class类型对应2个部分等）
	public int constant_type_part = 1;
	//当前读取的方法类型对应的片段（5个部分，access_flags，name_index，descriptor_index，attributes_count，attributes_info）
	public int field_or_method_info_part = 1;
	//当前读取的属性类型对应的片段
	public int attribute_info_part = 1;
	
	public int len = 0;
	public byte[] temp = null;
	
}
