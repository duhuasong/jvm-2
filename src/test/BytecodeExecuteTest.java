package test;

import jvm.bootstrap.MyJvm;

public class BytecodeExecuteTest {
	
	public static void main(String[] args) {
		MyJvm.java("test.MyTest");
	}
}
