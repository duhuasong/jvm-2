package jvm.template.beta03;

public class People {

	public String name;

	public int age;
	
	public long dick;

	public long getDick() {
		return dick;
	}

	public void setDick(long dick) {
		this.dick = dick;
	}

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
	
	/**
	 * return name+","+age; 调用的是StringBuilder的append方法，这里简化为：
	 * return name;
	 */
	@Override
	public String toString() {
		return name;
	}

}
