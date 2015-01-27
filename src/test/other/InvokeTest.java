package test.other;

public class InvokeTest {

	public static void main(String[] args)
	{
		test();
	}

	public static void test()
	{
		getCaller();
	}

	public static void getCaller()
	{
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
			StackTraceElement s = stack[1];
			System.out.format(" ClassName:%d\t%s\n", 1, s.getClassName());
			System.out.format("MethodName:%d\t%s\n", 1, s.getMethodName());
			System.out.format("  FileName:%d\t%s\n", 1, s.getFileName());
			System.out.format("LineNumber:%d\t%s\n\n", 1, s.getLineNumber());
	}
}
