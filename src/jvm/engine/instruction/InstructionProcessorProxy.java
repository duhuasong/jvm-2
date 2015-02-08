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
		boolean isSynchronizedMethod = javaStack.getCurrentFrame().getMethod().isSynchronized();
		String prefix = isSynchronizedMethod ? "[synchronized]" : "";
		System.out.println("********��ʼִ��"+prefix+"����["+javaStack.getCurMethodName()+"]�е��ֽ��� ["+instruct.opcode+"]"+(instruct.opcodeNum==null?"":"["+instruct.opcodeNum+"]"));;
		instructionProcessor.execute(instruct, javaStack);
	}

}
