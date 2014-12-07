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
 * 把类的信息加载到Memory中
 * @author yangrui
 *
 */
public class TestClassLoader implements IClassLoader{
	
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
		
		assembleClassBaseMethods(classInfo);

		return classInfo;
	}

	private  void assembleClassBaseMethods(ClassInfo classInfo) {
		
		MethodInfo mainMethod = new MethodInfo();
		MethodInfo addMethod = new MethodInfo();
		
		//设置main方法
		List<Instruction> mainInstruct = new ArrayList<Instruction>();
		mainInstruct.add(new Instruction("iconst_1", null));
		mainInstruct.add(new Instruction("istore_1", null));
		mainInstruct.add(new Instruction("bipush", "11"));
		mainInstruct.add(new Instruction("istore_2", null));
		mainInstruct.add(new Instruction("iload_1", null));
		mainInstruct.add(new Instruction("iload_2", null));
		mainInstruct.add(new Instruction("invokestatic",addMethod));// 第三个代表操作数中的两个参数
		mainInstruct.add(new Instruction("istore_3", null));
		mainInstruct.add(new Instruction("return", null));
		mainMethod.setClassInfo(classInfo);
		mainMethod.setMethodInstructions(mainInstruct);
		mainMethod.setName("main");
		mainMethod.setScope(Constants.Scope.PUBLIC);
		mainMethod.setStatic(true);
		//设置方法参数
		ParameterDescriptor param = new ParameterDescriptor();
		param.addInput(Constants.VarType.STRING_ARRAY);
		param.addOutput(Constants.VarType.VOID);
		mainMethod.setDescriptor(param);
		
		
		//设置add方法
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
		//设置方法参数
		ParameterDescriptor param2 = new ParameterDescriptor();
		param2.addInput(Constants.VarType.INTEGER);
		param2.addInput(Constants.VarType.INTEGER);
		param2.addOutput(Constants.VarType.INTEGER);
		addMethod.setDescriptor(param2);
		
		
		//设置class的methods
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		methods.add(mainMethod);
		methods.add(addMethod);
		classInfo.setMethods(methods);
		
	}
	
	/**
	 * 如果类里面有个main方法，把该方法加入Memory.entrancesMethods中
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
