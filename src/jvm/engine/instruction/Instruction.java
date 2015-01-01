package jvm.engine.instruction;

public class Instruction {
	
	public String opcode;
	
	public Object opcodeNum;
	


	public Instruction(String opcode, Object opcodeNum) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
	}

	public Instruction(String opcode) {
		super();
		this.opcode = opcode;
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


	

	@Override
	public String toString() {
		if(opcodeNum == null){
			return opcode;
		}
		return opcode + " : " + opcodeNum;
	}
	

}
