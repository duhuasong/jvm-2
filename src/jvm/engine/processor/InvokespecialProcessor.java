package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.MethodInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokespecial")
public class InvokespecialProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		//pop������
		OperandVariable[] paramaters = javaStack.popOprandArray(MethodUtil.parseMethodInputSize(method_descripter));
		//pop��InstanceInfo
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popOprand().getValue();
	
		//���ݷ�����descripter����classFile���ҵ���Ӧ�ķ���
		MethodInfo methodInfo = MethodUtil.searchMethod(method_descripter); 
		//�Ѹ÷�����instanceInfo��push����ǰ֡��
		javaStack.createAndPushFrame(methodInfo,instanceInfo);
		//��֮ǰջ֡�������е���������pop���������ջ֡�ı��ر������0��1��2...
		javaStack.pushOprandArray(paramaters);
		javaStack.executeFrame();
	}

}
