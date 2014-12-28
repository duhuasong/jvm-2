package test.template.templateEnd;

public class Woman extends People {
	
	public String breastSize;//A£¬B£¬C£¬D£¬E£¬F£¬G£¬H
	
	public Woman(String name, int age) {
		super(name,age);
	}

	@Override
	public void pee() {
		System.out.println("¶××ÅÄòÄò");
	}

	public String getBreastSize() {
		return breastSize;
	}

	public void setBreastSize(String breastSize) {
		this.breastSize = breastSize;
	}

}
