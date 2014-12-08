package jvm.classloader.classfile;

public class ClassFileCounter {
	
	public ClassFileCounter(int i) {
		counter = i;
	}

	public ClassFileCounter() {
		
	}

	public int counter = 0;

	public ElementDescripter getCurElement() {
		return ClassFileDescripter.classElements.get(counter++);
	}
	
	

}
