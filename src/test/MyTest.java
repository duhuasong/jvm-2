package test;

public class MyTest {

	public static void main(String[] args) {
		int i = 1;
		int j = 11;
		int c = add(i,j);
		int d = add2(j,i);
		int e = selfadd(i);

	}

	public static int add(int i,int j) {
		return i+j;
	}
	
	public static int add2(int i,int j) {
		return i+j;
	}
	
	public static int selfadd(int i) {
		return i++;
	}

}
