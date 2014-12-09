package jvm.memory.classinfo;

import java.util.ArrayList;
import java.util.List;

public class ParameterDescriptor {
	
	private List<String> inputList = new ArrayList<String>();
	
	private List<String> outputList = new ArrayList<String>();

	public List<String> getInputList() {
		return inputList;
	}

	public void setInputList(List<String> inputList) {
		this.inputList = inputList;
	}

	public List<String> getOutputList() {
		return outputList;
	}

	public void setOutputList(List<String> outputList) {
		this.outputList = outputList;
	}
	
	public void addInput(String paramType) {
		inputList.add(paramType);
	}
	
	public void addOutput(String paramType) {
		outputList.add(paramType);
	}
	
	
}
