package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackFrame {
	//�ֲ�������
	private List<Object> localVariableTable = new ArrayList<Object>();
	//������ջ
	private Stack<Object> operandStack = new Stack<Object>();
	//�����ص�����
	private Object referenceToConstantPool;
	
	private int frameCounter = 0;
	
	

}
