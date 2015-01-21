package test.template.beta03;

public class DateMain {

	public static void main(String[] args) {
		People people = new People("hehe", 26);
		people.setDick(1234567890l);
		System.out.println(people.getDick());
	}

}
