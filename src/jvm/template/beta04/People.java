package jvm.template.beta04;

public class People {

	public String name;

	public int age;
	
	public People(String name, int age) {
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
	public void pie() {
		System.out.println("people pie.");
	}
	/**
	 * return name+","+age; 调用的是StringBuilder的append方法，这里简化为：
	 * return name;
	 */
	@Override
	public String toString() {
		return name;
	}

}
