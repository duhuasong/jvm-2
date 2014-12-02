package jvm.classloader.impl;

import java.util.ArrayList;
import java.util.List;

import jvm.classloader.inter.BaseClassLoader;
import jvm.instruction.Instruction;
import jvm.memory.ClassInfo;
import jvm.memory.Memory;
import jvm.memory.StaticMethod;
/**
 * 把类的信息加载到Memory中
 * @author yangrui
 *
 */
public class TestClassLoader implements BaseClassLoader{
	
	/**
	 * 加载类，如：
	 * className = "test.MyTest";
	 * @param className
	 */
	public void loadClass(String className) {

		ClassInfo myTestClass = assembleClass(className);
		
		//设置Memory的classPool
		Memory.classPool.put(className, myTestClass);
		//找到入口main方法，如果有的话，设置Memory中的entrancesMethods
		entrancesMethodsFilter(myTestClass);

	}

	private  ClassInfo assembleClass(String className) {
		
		ClassInfo classInfo = new ClassInfo(className);
		
		assembleClassStaticMethods(classInfo);

		

		return classInfo;
	}

	private  void assembleClassStaticMethods(ClassInfo classInfo) {
		//设置main方法
		StaticMethod mainMethod = new StaticMethod();
		List<Instruction> mainInstruct = new ArrayList<Instruction>();
		mainInstruct.add(new Instruction("iconst_1", null));
		mainInstruct.add(new Instruction("istore_1", null));
		mainInstruct.add(new Instruction("bipush", 11));
		mainInstruct.add(new Instruction("istore_2", null));
		mainInstruct.add(new Instruction("iload_1", null));
		mainInstruct.add(new Instruction("iload_2", null));
		mainInstruct.add(new Instruction("invokestatic", "test.MyTest.add", 2));// 第三个代表操作数中的两个参数
		mainInstruct.add(new Instruction("istore_3", null));
		mainInstruct.add(new Instruction("return", null));
		mainMethod.setClassInfo(classInfo);
		mainMethod.setMethodInstructions(mainInstruct);
		mainMethod.setMethodName("test.MyTest.main");
		
		//设置add方法
		StaticMethod addMethod = new StaticMethod();
		List<Instruction> addInstruct = new ArrayList<Instruction>();
		addInstruct.add(new Instruction("iload_0", null));
		addInstruct.add(new Instruction("iload_1", null));
		addInstruct.add(new Instruction("iadd", null));
		addInstruct.add(new Instruction("ireturn", null));
		addMethod.setClassInfo(classInfo);
		addMethod.setMethodInstructions(addInstruct);
		addMethod.setMethodName("test.MyTest.add");
		
		//设置class的staticMethod
		List<StaticMethod> staticMethods = new ArrayList<StaticMethod>();
		staticMethods.add(mainMethod);
		staticMethods.add(addMethod);
		classInfo.setStaticMethods(staticMethods);
		
	}
	
	/**
	 * 如果类里面有个main方法，把该方法加入Memory.entrancesMethods中
	 * @param classInfo
	 */
	private  void entrancesMethodsFilter(ClassInfo classInfo) {
		
		if(classInfo.getStaticMethods()!=null){
			for(StaticMethod staticMethod : classInfo.getStaticMethods()){
				if(staticMethod.getMethodName().endsWith(".main")){
					try {
						Memory.entrancesMethods.put(staticMethod);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}


}
