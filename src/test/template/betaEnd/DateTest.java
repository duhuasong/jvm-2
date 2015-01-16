package test.template.betaEnd;

public class DateTest {
	
	public static void main(String[] args) {
		
		People people = new Man("zhaohuan",25);
		people.pee();
		
		Man man = (Man)people;
		man.setDickLength(1234567890111213l);
		
		System.out.println(man.getDickLength());
	}

}
