package jvm.classloader;

import java.util.ArrayList;
import java.util.List;

import jvm.engine.instruction.Instruction;
import jvm.memory.ClassInfo;
import jvm.memory.Memory;
import jvm.memory.MethodInfo;
import jvm.memory.ParameterDescriptor;
import jvm.util.Constants;
import jvm.util.MethodUtil;
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
		
		MethodInfo mainMethod = new MethodInfo();
		MethodInfo addMethod = new MethodInfo();
		
		//����main����
		List<Instruction> mainInstruct = new ArrayList<Instruction>();
		mainInstruct.add(new Instruction("iconst_1", null));
		mainInstruct.add(new Instruction("istore_1", null));
		mainInstruct.add(new Instruction("bipush", "11"));
		mainInstruct.add(new Instruction("istore_2", null));
		mainInstruct.add(new Instruction("iload_1", null));
		mainInstruct.add(new Instruction("iload_2", null));
		mainInstruct.add(new Instruction("invokestatic",addMethod));// ����������������е���������
		mainInstruct.add(new Instruction("istore_3", null));
		mainInstruct.add(new Instruction("return", null));
		mainMethod.setClassInfo(classInfo);
		mainMethod.setMethodInstructions(mainInstruct);
		mainMethod.setName("main");
		mainMethod.setScope(Constants.Scope.PUBLIC);
		mainMethod.setStatic(true);
		//���÷�������
		ParameterDescriptor param = new ParameterDescriptor();
		param.addInput(Constants.VarType.STRING_ARRAY);
		param.addOutput(Constants.VarType.VOID);
		mainMethod.setDescriptor(param);
		
		
		//����add����
		List<Instruction> addInstruct = new ArrayList<Instruction>();
		addInstruct.add(new Instruction("iload_0", null));
		addInstruct.add(new Instruction("iload_1", null));
		addInstruct.add(new Instruction("iadd", null));
		addInstruct.add(new Instruction("ireturn", null));
		addMethod.setClassInfo(classInfo);
		addMethod.setMethodInstructions(addInstruct);
		addMethod.setName("add");
		addMethod.setScope(Constants.Scope.PUBLIC);
		addMethod.setStatic(true);
		//���÷�������
		ParameterDescriptor param2 = new ParameterDescriptor();
		param2.addInput(Constants.VarType.INTEGER);
		param2.addInput(Constants.VarType.INTEGER);
		param2.addOutput(Constants.VarType.INTEGER);
		addMethod.setDescriptor(param2);
		
		
		//����class��methods
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		methods.add(mainMethod);
		methods.add(addMethod);
		classInfo.setMethods(methods);
		
	}
	
	/**
	 * ����������и�main�������Ѹ÷�������Memory.entrancesMethods��
	 * @param classInfo
	 */
	private  void entrancesMethodsFilter(ClassInfo classInfo) {
		if(classInfo.getMethods()!=null){
			for(MethodInfo method : classInfo.getMethods()){
				if(MethodUtil.isEntryMethod(method)){
					try {
						Memory.entrancesMethods.put(method);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}


}
