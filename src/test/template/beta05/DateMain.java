package test.template.beta05;

import java.util.ArrayList;
import java.util.List;

public class DateMain {
	
	public static void main(String[] args) {
		List<People> list = new ArrayList<People>();
		list.add(new People("yangrui",25));
		list.add(new Man("zhaohuan",27));
		System.out.println(list.size());
	}

}
