package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.MethodInfo;
import jvm.stack.JavaStack;
import jvm.util.MethodUtil;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * 
 * 指令：invokestatic <method-spec>
 * <method-spec>如：java/lang/System/exit(I)V，由三部分组成： a classname, a methodname and a descriptor
 * 
 * (参考：http://cs.au.dk/~mis/dOvs/jvmspec/ref--34.html)
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "invokestatic")
public class InvokestaticProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		
		String method_descripter = (String)instruct.getOpcodeNum();
		
		MethodInfo mi = MethodUtil.searchMethod(method_descripter);
		//创建的新的栈帧
		javaStack.createAndPushFrame(mi);
		//把之前栈帧操作数中的所有数据pop，存放在新栈帧的本地变量表的0、1、2...
		javaStack.putPreOprand2CurVarTable();
		//开始执行新栈帧的指令
		javaStack.executeFrame();
	}

}
