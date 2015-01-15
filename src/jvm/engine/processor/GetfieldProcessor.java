package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.FieldInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.annotation.ProcessorAnnotation;

@ProcessorAnnotation(byteCode = "getfield")
public class GetfieldProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//根据field_descriptor获取field
		String field_descriptor = (String)instruct.getOpcodeNum();
		//pop出instanceInfo
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popOprand().getValue();
		FieldInfo key = instanceInfo.getFieldKey(field_descriptor);
		Object value = instanceInfo.fieldValues.get(key);
		javaStack.pushOprand(new OperandVariable(key.getDescriptor(), value));
	}
	
}