package jvm.util.factory;

import java.util.HashMap;
import java.util.Map;

import jvm.engine.InstructionProcessor;

public class ProcessorFactory {
	
	private static Map<Class<?>,InstructionProcessor> map = new HashMap<Class<?>,InstructionProcessor>();
	/**
	 * @param processorClass
	 * @return
	 */
	public static InstructionProcessor createProcessor(
			Class<?> processorClass) {
		InstructionProcessor processor = map.get(processorClass);
		if(processor == null){
			try {
				processor =  (InstructionProcessor) processorClass.newInstance();
				map.put(processorClass, processor);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return processor;
	}
	
}
