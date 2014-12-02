package jvm.classloader.impl;

import java.util.ArrayList;
import java.util.List;

import jvm.classloader.inter.BaseClassLoader;
import jvm.instruction.Instruction;
import jvm.memory.ClassInfo;
import jvm.memory.Memory;
import jvm.memory.StaticMethod;
/**
 * �������Ϣ���ص�Memory��
 * @author yangrui
 *
 */
public class TestClassLoader implements BaseClassLoader{
	
	/**
	 * �����࣬�磺
	 * className = "test.MyTest";
	 * @param className
	 */
	public void loadClass(String className) {

		ClassInfo myTestClass = assembleClass(className);
		
		//����Memory��classPool
		Memory.classPool.put(className, myTestClass);
		//�ҵ����main����������еĻ�������Memory�е�entrancesMethods
		entrancesMethodsFilter(myTestClass);

	}

	private  ClassInfo assembleClass(String className) {
		
		ClassInfo classInfo = new ClassInfo(className);
		
		assembleClassStaticMethods(classInfo);

		

		return classInfo;
	}

	private  void assembleClassStaticMethods(ClassInfo classInfo) {
		//����main����
		StaticMethod mainMethod = new StaticMethod();
		List<Instruction> mainInstruct = new ArrayList<Instruction>();
		mainInstruct.add(new Instruction("iconst_1", null));
		mainInstruct.add(new Instruction("istore_1", null));
		mainInstruct.add(new Instruction("bipush", 11));
		mainInstruct.add(new Instruction("istore_2", null));
		mainInstruct.add(new Instruction("iload_1", null));
		mainInstruct.add(new Instruction("iload_2", null));
		mainInstruct.add(new Instruction("invokestatic", "test.MyTest.add", 2));// ����������������е���������
		mainInstruct.add(new Instruction("istore_3", null));
		mainInstruct.add(new Instruction("return", null));
		mainMethod.setClassInfo(classInfo);
		mainMethod.setMethodInstructions(mainInstruct);
		mainMethod.setMethodName("test.MyTest.main");
		
		//����add����
		StaticMethod addMethod = new StaticMethod();
		List<Instruction> addInstruct = new ArrayList<Instruction>();
		addInstruct.add(new Instruction("iload_0", null));
		addInstruct.add(new Instruction("iload_1", null));
		addInstruct.add(new Instruction("iadd", null));
		addInstruct.add(new Instruction("ireturn", null));
		addMethod.setClassInfo(classInfo);
		addMethod.setMethodInstructions(addInstruct);
		addMethod.setMethodName("test.MyTest.add");
		
		//����class��staticMethod
		List<StaticMethod> staticMethods = new ArrayList<StaticMethod>();
		staticMethods.add(mainMethod);
		staticMethods.add(addMethod);
		classInfo.setStaticMethods(staticMethods);
		
	}
	
	/**
	 * ����������и�main�������Ѹ÷�������Memory.entrancesMethods��
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
