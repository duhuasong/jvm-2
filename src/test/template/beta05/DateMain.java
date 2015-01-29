package test.template.beta05;

public class DateMain {
	
	public static void main(String[] args) {
		
		People p = new Man("zhaohuan",25);
		p.pee();
		
		Man man = (Man)p;
		man.setDickLength(1234567890111213l);
		
		System.out.println(man.getDickLength());
	}

}
