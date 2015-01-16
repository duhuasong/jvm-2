package test.template.betaEnd;

public class Man extends People {
	
	public long dickLength;
	
	public Man(String name, int age) {
		super(name,age);
	}
	@Override
	public void pee() {
		System.out.println("up pee");
	}
	
	public long getDickLength() {
		return dickLength;
	}
	public void setDickLength(long dickLength) {
		this.dickLength = dickLength;
	}

}
