package jvm.util.factory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jvm.engine.instruction.InstructionProcessor;
import jvm.util.Constants;
import jvm.util.annotation.ProcessorAnnotation;
import jvm.util.common.FileUtil;

public class ProcessorFactory {
	
	private static String processorPackagePath = Constants.classpath+"/jvm/engine/processor";
	
	private static String processorPackageName = "jvm.engine.processor";
	
	private static Map<String,InstructionProcessor> map = new HashMap<String, InstructionProcessor>();
	
	public static void init() {
		List<String> fileNameList = FileUtil.getClassNames(processorPackagePath);
		String fullClassName = null;
		for(String className : fileNameList){
			fullClassName = processorPackageName +"."+ className;
			Class<?> c = null;
			try {
				c = Class.forName(fullClassName);
				Annotation[] annos = c.getAnnotations();
				String byteCodeLike = ((ProcessorAnnotation)annos[0]).byteCode();
				map.put(byteCodeLike, (InstructionProcessor)c.newInstance());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	public static InstructionProcessor createProcessor(String byteCode) {
		InstructionProcessor processor = map.get(byteCode);
		if(null == processor){
			byteCode = byteCode.split("\\_")[0];
			processor = map.get(byteCode);
		}
		if(null == processor){
			byteCode = byteCode + "_";
			processor = map.get(byteCode);
		}
		return processor;
	}
	
	
}
