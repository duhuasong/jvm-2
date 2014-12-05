package jvm.classloader;

import java.util.ArrayList;
import java.util.List;

import jvm.engine.instruction.Instruction;
import jvm.memory.MethodInfo;
import jvm.memory.ClassInfo;
import jvm.memory.Memory;
/**
 * �������Ϣ���ص�Memory��
 * @author yangrui
 *
 */
public class TestClassLoader implements IClassLoader{
	
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
		
		assembleClassBaseMethods(classInfo);

		return classInfo;
	}

	private  void assembleClassBaseMethods(ClassInfo classInfo) {
		//����main����
		MethodInfo mainMethod = new MethodInfo();
		List<Instruction> mainInstruct = new ArrayList<Instruction>();
		mainInstruct.add(new Instruction("iconst_1", null));
		mainInstruct.add(new Instruction("istore_1", null));
		mainInstruct.add(new Instruction("bipush", "11"));
		mainInstruct.add(new Instruction("istore_2", null));
		mainInstruct.add(new Instruction("iload_1", null));
		mainInstruct.add(new Instruction("iload_2", null));
		mainInstruct.add(new Instruction("invokestatic", "test.MyTest.add", "2"));// ����������������е���������
		mainInstruct.add(new Instruction("istore_3", null));
		mainInstruct.add(new Instruction("return", null));
		mainMethod.setClassInfo(classInfo);
		mainMethod.setMethodInstructions(mainInstruct);
		mainMethod.setName("test.MyTest.main");
		
		//����add����
		MethodInfo addMethod = new MethodInfo();
		List<Instruction> addInstruct = new ArrayList<Instruction>();
		addInstruct.add(new Instruction("iload_0", null));
		addInstruct.add(new Instruction("iload_1", null));
		addInstruct.add(new Instruction("iadd", null));
		addInstruct.add(new Instruction("ireturn", null));
		addMethod.setClassInfo(classInfo);
		addMethod.setMethodInstructions(addInstruct);
		addMethod.setName("test.MyTest.add");
		
		//����class��BaseMethod
		List<MethodInfo> BaseMethods = new ArrayList<MethodInfo>();
		BaseMethods.add(mainMethod);
		BaseMethods.add(addMethod);
		classInfo.setMethods(BaseMethods);
		
	}
	
	/**
	 * ����������и�main�������Ѹ÷�������Memory.entrancesMethods��
	 * @param classInfo
	 */
	private  void entrancesMethodsFilter(ClassInfo classInfo) {
		if(classInfo.getMethods()!=null){
			for(MethodInfo BaseMethod : classInfo.getMethods()){
				if(BaseMethod.getName().endsWith(".main")){
					try {
						Memory.entrancesMethods.put(BaseMethod);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}


}
