package test.template.betaEnd;

public class People {
	
	public String name;
	
	public int age;
	
	public void pee(){
		System.out.println("pipi");
	};
	
	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public People(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

}
