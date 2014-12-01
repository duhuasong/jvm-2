package jvm.instruct;

public class Instruction {
	
	public Object opcode;
	
	public Object opcodeNum;
	
	public Object opcodeNum2;

	public Instruction(Object opcode, Object opcodeNum) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
	}

	public Instruction(Object opcode, Object opcodeNum, Object opcodeNum2) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
		this.opcodeNum2 = opcodeNum2;
	}

	
	

}
