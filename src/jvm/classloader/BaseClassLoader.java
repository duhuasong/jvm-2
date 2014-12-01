package jvm.classloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jvm.instruct.Instruction;
import jvm.memory.ClassInfo;
import jvm.memory.Memory;

public class BaseClassLoader {

	public static void loadClass(String className) {

		className = "test.MyTest";

		ClassInfo myTestClass = assembleClass(className);
		
		Memory.classPool.put(className, myTestClass);

	}

	private static ClassInfo assembleClass(String className) {
		
		ClassInfo myTestClassInfo = new ClassInfo(className);
		
		assembleClassStaticMethods(myTestClassInfo);

		return myTestClassInfo;
	}

	private static void assembleClassStaticMethods(ClassInfo myTestClassInfo) {
		List<Instruction> mainMethodInfo = new ArrayList<Instruction>();
		mainMethodInfo.add(new Instruction("iconst_1", null));
		mainMethodInfo.add(new Instruction("istore_1", null));
		mainMethodInfo.add(new Instruction("bipush", 11));
		mainMethodInfo.add(new Instruction("istore_2", null));
		mainMethodInfo.add(new Instruction("iload_1", null));
		mainMethodInfo.add(new Instruction("iload_2", null));
		mainMethodInfo.add(new Instruction("invokestatic", "test.MyTest.add", 2));// 第三个代表操作数中的两个参数
		mainMethodInfo.add(new Instruction("istore_3", null));
		mainMethodInfo.add(new Instruction("return", null));

		List<Instruction> addMethodInfo = new ArrayList<Instruction>();
		addMethodInfo.add(new Instruction("iload_0", null));
		addMethodInfo.add(new Instruction("iload_1", null));
		addMethodInfo.add(new Instruction("iadd", null));
		addMethodInfo.add(new Instruction("ireturn", null));

		Map<String, List<Instruction>> staticMethods = new HashMap<String, List<Instruction>>();
		staticMethods.put("test.MyTest.main", mainMethodInfo);
		staticMethods.put("test.MyTest.add", addMethodInfo);
		myTestClassInfo.setStaticMethods(staticMethods);
		
		entrancesMethodsFilter(myTestClassInfo);
		
	}
	
	private static void entrancesMethodsFilter(ClassInfo myTestClassInfo) {
		
		
	}

}
