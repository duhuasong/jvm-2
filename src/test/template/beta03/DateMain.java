package test.template.beta03;

public class DateMain {

	public static void main(String[] args) {
		People people = new People("hehe", 26);
		people.setDick(12345678901112l);
		System.out.println(people.getDick());
	}

}
