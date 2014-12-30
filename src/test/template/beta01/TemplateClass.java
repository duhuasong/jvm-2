package test.template.beta01;

public class TemplateClass {

	public static void main(String[] args) {
		int j = 12; 
		int i = 13;
		int c= add(i,j);
		System.out.println(c);
	} 

	public static int add(int i,int j) {
		return i+j;
	}

}
