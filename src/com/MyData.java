package com;

public class MyData {
	
	public Oprand[] instrus01 = new Oprand[]{
			new Oprand("bipush", 5),//将5压入操作数栈
			new Oprand("istore_0", null)//将操作数栈顶数据出栈，存入局部变量表0位
	};


}
