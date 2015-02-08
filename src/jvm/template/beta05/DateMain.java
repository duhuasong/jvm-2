package jvm.template.beta05;

public class DateMain {

	public static void main(String[] args) {
		int j = 12; 
		int i = 13;
		int age = add(i,j);
		People p = new Man("hehe", age);
		p.pie();
		p.setAge(30);
		System.out.println(p.getAge());
	}
	
	public static synchronized int add(int i,int j) {
		return i+j;
	}

}
