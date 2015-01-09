package test.other.anno;

import java.lang.annotation.Annotation;

public class AnnTest {
	public static void main(String[] args) {
		
		Class c = TestNewProcessor.class;
		
		Annotation[] b = c.getAnnotations();
		
		System.out.println(((ProcessorAnnotation)b[0]).byteCodeLike());
		
	}

}
