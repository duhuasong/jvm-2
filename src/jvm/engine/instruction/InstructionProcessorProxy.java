package jvm.engine.instruction;

import jvm.stack.JavaStack;

public class InstructionProcessorProxy implements InstructionProcessor {
	
	private InstructionProcessor instructionProcessor;

	public InstructionProcessorProxy(InstructionProcessor instructionProcessor) {
		super();
		this.instructionProcessor = instructionProcessor;
	}
	
	@Override
	public void execute(Instruction instruct, JavaStack javaStack) {
		System.out.println("********��ʼִ�з���["+javaStack.getCurMethodName()+"]�е��ֽ��� ["+instruct.opcode+"]"+(instruct.opcodeNum==null?"":"["+instruct.opcodeNum+"]"));;
		instructionProcessor.execute(instruct, javaStack);
	}

}
