package jvm.engine.instruction;

public class Instruction {
	
	public String opcode;
	
	//mergeByteCodeȫ����ת��Ϊstring����
	public String opcodeNum;


	public Instruction(String opcode, String opcodeNum) {
		super();
		this.opcode = opcode;
		this.opcodeNum = opcodeNum;
	}

	public Instruction(String opcode) {
		super();
		this.opcode = opcode;
	}

	@Override
	public String toString() {
		if(opcodeNum == null){
			return opcode;
		}
		return opcode + " : " + opcodeNum;
	}
	

}
