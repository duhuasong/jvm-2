package jvm.classloader.help;
/**
 * ��ȡclass�ļ��õ���һЩ��������
 * @author yangrui
 *
 */
public class TempVariable {
	public int constant_pool_count = 0;
	public int constant_pool_pointer = 1;
	//��ǰ��ȡ�ĳ�������
	public String constant_type = null;
	//��ǰ��ȡ�ĳ������Ͷ�Ӧ��Ƭ�Σ�utf8��Ӧ3��class���Ͷ�Ӧ2�����ֵȣ�
	public int constant_type_part = 1;
	//��ǰ��ȡ�ķ������Ͷ�Ӧ��Ƭ�Σ�5�����֣�access_flags��name_index��descriptor_index��attributes_count��attributes_info��
	public int field_or_method_info_part = 1;
	//��ǰ��ȡ���������Ͷ�Ӧ��Ƭ��
	public int attribute_info_part = 1;
	
	public int len = 0;
	public byte[] temp = null;
	
}
