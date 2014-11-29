package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackFrame {
	//局部变量表
	private List<Object> localVariableTable = new ArrayList<Object>();
	//操作数栈
	private Stack<Object> operandStack = new Stack<Object>();
	//常量池的引用
	private Object referenceToConstantPool;
	
	private int frameCounter = 0;
	
	

}
