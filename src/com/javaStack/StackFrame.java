package com.javaStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.localVarTable.LocalVariableTable;

public class StackFrame {
	//�ֲ�������
	private LocalVariableTable localVariableTable;
	//������ջ
	private Stack<Object> operandStack = new Stack<Object>();
	//�����ص�����
	private Object referenceToConstantPool;
	
	private int frameCounter = 0;
	
	

}
