package jvm.engine.instruction;

public class Instruction {
	
	public String opcode;
	
	public Object opcodeNum;
	
	public Object opcodeNum2;
	
	

	public Instruction(String opcode, Object opcodeNum, Object opcodeNum2) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
		this.opcodeNum2 = opcodeNum2;
	}

	public Instruction(String opcode, Object opcodeNum) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public Object getOpcodeNum() {
		return opcodeNum;
	}

	public void setOpcodeNum(Object opcodeNum) {
		this.opcodeNum = opcodeNum;
	}

	public Object getOpcodeNum2() {
		return opcodeNum2;
	}

	public void setOpcodeNum2(Object opcodeNum2) {
		this.opcodeNum2 = opcodeNum2;
	}

	

	
	

}
