package jvm.engine.instruction;

public class Instruction {
	
	public String opcode;
	
	public String opcodeNum;
	
	public String opcodeNum2;

	public Instruction(String opcode, String opcodeNum) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
	}

	public Instruction(String opcode, String opcodeNum, String opcodeNum2) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
		this.opcodeNum2 = opcodeNum2;
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

	public void setOpcodeNum(String opcodeNum) {
		this.opcodeNum = opcodeNum;
	}

	public String getOpcodeNum2() {
		return opcodeNum2;
	}

	public void setOpcodeNum2(String opcodeNum2) {
		this.opcodeNum2 = opcodeNum2;
	}
	
	

	
	

}
