package com;

public class DataTest {
	
	public Instru[] instrus_list = new Instru[]{
			new Instru("iconst_1", null),// push 1到操作栈。大于5的int值会用到 bipush <i> 指令。
			new Instru("istore_0", null),// pop 顶元素，存储到index=0的本地变量。
			new Instru("iconst_2", null),
			new Instru("istore_1", null),
			new Instru("iload_0", null),
			new Instru("iload_1", null),
			new Instru("iadd", null),
			new Instru("istore_2", null),
	};


}
