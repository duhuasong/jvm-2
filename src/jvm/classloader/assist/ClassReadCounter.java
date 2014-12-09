package jvm.classloader.assist;

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
