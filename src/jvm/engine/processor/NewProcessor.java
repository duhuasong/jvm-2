package jvm.engine.processor;

import jvm.classloader.InterfaceClassLoader;
import jvm.classloader.impl.BaseClassLoader;
import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.Memory.MethodArea;
import jvm.memory.classinfo.ClassInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.Constants;
import jvm.util.common.StringUtil;
import jvm.util.factory.ClassLoaderFactory;
/**
 * ָ�new <class><n>
 * 1�����classû�����ڴ��У��Ȱ�class���ص��ڴ���
 * 2������һ��class��Ӧ��ʵ��
 * 3������class��Ĭ�ϵ�init����
 * @author yangrui
 *
 */
public class NewProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//���ڴ��г��Ի�ȡclass
		String opcodeNum = (String)instruct.getOpcodeNum();
		String className = StringUtil.replacePathToClass(opcodeNum);
		ClassInfo targetClassIno = MethodArea.getClassInfo(className);
		//����ڴ��л�û�м���class������ظ�class
		if(targetClassIno == null){
			InterfaceClassLoader loader = ClassLoaderFactory.createClassLoader(BaseClassLoader.class);
			targetClassIno = loader.loadClass(className);
		}
		InstanceInfo instance = new InstanceInfo(targetClassIno);
		//��ʵ������oprand stack
		OperandVariable opNum = new OperandVariable(Constants.VarType.Object_Type,instance);
		javaStack.pushCurrentFrameOprandStack(opNum);
	}

}
