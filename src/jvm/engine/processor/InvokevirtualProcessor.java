package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.stack.JavaStack;
/**
 * getstatic  java/lang/System.out:Ljava/io/PrintStream;
   invokevirtual  java/io/PrintStream.println:(I)V
   1�����ݲ�������(I)���Ӳ�����ջ�аѲ���pop��Ȼ��pop����������
   2���ڶ�������������
   3��ִ�з�����ע�������
 * @author yangrui
 *
 */
public class InvokevirtualProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
	}

}
