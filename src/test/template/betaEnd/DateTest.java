package test.template.betaEnd;

public class DateTest {
	
	public static void main(String[] args) {
		Man man = new Man("zhaohuan",25);
		Woman woman = new Woman("xueyi",22);
		boolean flag = date(man,woman);
		if(flag){
			System.out.println("date success");
		}else{
			System.out.println("date fail");
		}
		
	}

	private static boolean date(People man, People woman) {
		if(man.getAge()-woman.getAge() >= 1){
			return true;
		}
		return false;
	}

}
