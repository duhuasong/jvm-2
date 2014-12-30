package test.template.betaEnd;

public class Man extends People {
	
	public int dickLength;
	
	public Man(String name, int age) {
		super(name,age);
	}
	@Override
	public void pee() {
		System.out.println("Õ¾×ÅÄòÄò");
	}
	public int getDickLength() {
		return dickLength;
	}

	public void setDickLength(int dickLength) {
		this.dickLength = dickLength;
	}

}
