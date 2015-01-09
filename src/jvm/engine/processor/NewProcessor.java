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
 * 指令：new <class><n>
 * 1、如果class没有在内存中，先把class加载到内存中
 * 2、创建一个class对应的实例
 * 3、调用class的默认的init方法
 * @author yangrui
 *
 */
public class NewProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//从内存中尝试获取class
		String opcodeNum = (String)instruct.getOpcodeNum();
		String className = StringUtil.replacePathToClass(opcodeNum);
		ClassInfo targetClassIno = MethodArea.getClassInfo(className);
		//如果内存中还没有加载class，则加载该class
		if(targetClassIno == null){
			InterfaceClassLoader loader = ClassLoaderFactory.createClassLoader(BaseClassLoader.class);
			targetClassIno = loader.loadClass(className);
		}
		InstanceInfo instance = new InstanceInfo(targetClassIno);
		//把实例加入oprand stack
		OperandVariable opNum = new OperandVariable(Constants.VarType.Object_Type,instance);
		javaStack.pushCurrentFrameOprandStack(opNum);
	}

}
