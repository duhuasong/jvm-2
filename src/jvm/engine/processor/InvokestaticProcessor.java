package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.MethodInfo;
import jvm.stack.JavaStack;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * 
 * ָ�invokestatic <method-spec>
 * <method-spec>�磺java/lang/System/exit(I)V������������ɣ� a classname, a methodname and a descriptor
 * 
 * (�ο���http://cs.au.dk/~mis/dOvs/jvmspec/ref--34.html)
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokestatic")
public class InvokestaticProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		
		MethodInfo mi = MethodUtil.searchMethod(method_descripter);
		//�������µ�ջ֡
		javaStack.createAndPushFrame(mi);
		//��֮ǰջ֡�������е���������pop���������ջ֡�ı��ر������0��1��2...
		javaStack.putPreOprand2CurVarTable();
		//��ʼִ����ջ֡��ָ��
		javaStack.executeFrame();
	}

}
