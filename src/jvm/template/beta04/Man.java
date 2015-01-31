package jvm.template.beta04;

public class Man extends People {
	
	public long dick;


	public Man(String name, int age) {
		super(name, age);
	}
	

	public long getDick() {
		return dick;
	}

	public void setDick(long dick) {
		this.dick = dick;
	}
	
	@Override
	public void pie() {
		System.out.println("man pie.");
	}

}
