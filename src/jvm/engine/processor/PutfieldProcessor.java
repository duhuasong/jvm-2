package jvm.engine.processor;

import jvm.engine.instruction.Instruction;
import jvm.engine.instruction.InstructionProcessor;
import jvm.memory.classinfo.FieldInfo;
import jvm.memory.instanceinfo.InstanceInfo;
import jvm.stack.JavaStack;
import jvm.stack.operandStack.OperandVariable;
import jvm.util.annotation.ProcessorAnnotation;
/**
 * @author yangrui
 *
 */
@ProcessorAnnotation(byteCode = "putfield")
public class PutfieldProcessor implements InstructionProcessor {

	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		//根据field_descriptor获取field
		String field_descriptor = (String)instruct.opcodeNum;
		//pop出value和instanceInfo
		OperandVariable value = javaStack.popOprand();
		InstanceInfo instanceInfo = (InstanceInfo)javaStack.popOprand().getValue();
		FieldInfo key = instanceInfo.getFieldKey(field_descriptor);
		//field赋值
		instanceInfo.putFieldValue(key,value.getValue());
		
	}

}
