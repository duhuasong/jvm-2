package jvm.classloader.classfile;

public class ClassReadCounter {
	
	public ClassReadCounter(int i) {
		counter = i;
	}

	public ClassReadCounter() {
		
	}

	public int counter = 0;

	public ElementDescripter getCurElement() {
		return ClassDescripter.classElements.get(counter++);
	}
	
	

}
