package com;

public class DataTest {
	
	public Instru[] instrus_list = new Instru[]{
			new Instru("iconst_1", null),// push 1������ջ������5��intֵ���õ� bipush <i> ָ�
			new Instru("istore_0", null),// pop ��Ԫ�أ��洢��index=0�ı��ر�����
			new Instru("iconst_2", null),
			new Instru("istore_1", null),
			new Instru("iload_0", null),
			new Instru("iload_1", null),
			new Instru("iadd", null),
			new Instru("istore_2", null),
	};


}
