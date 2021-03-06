package jvm.classloader.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classfile.FieldMethodFile;
import jvm.classloader.classfile.attribute.CodeAttributeFile;
import jvm.classloader.classfile.attribute.CommonAttributeFile;
import jvm.classloader.tool.ByteCodeMap;
import jvm.engine.instruction.Instruction;
import jvm.memory.Memory;
import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.FieldInfo;
import jvm.memory.classinfo.MethodInfo;
import jvm.util.AccessFlagUtil;
import jvm.util.Constants;
import jvm.util.MethodUtil;
import jvm.util.common.ByteHexUtil;
import jvm.util.common.LogUtil;
import jvm.util.common.StringUtil;
import jvm.util.enums.ConstantTypeEnum;
import jvm.util.exception.JvmException;

public abstract class AbstractClassLoader implements InterfaceClassLoader {

	@Override
	public ClassInfo loadClass(String className) {
		
		ClassFile classFile = null;
		try {
			classFile = loadClassFile(className);
		} catch (JvmException e) {
			e.printStackTrace();
		}
		
		translateClassFile(classFile);
		
		loadSuperClassInfo(classFile);
		
		LogUtil.println("print.classfile", classFile.toString());
		
		ClassInfo classInfo = copyClassFileToClassInfo(classFile);
		
		LogUtil.println("print.classinfo", classInfo.toString());
		
		entrancesMethodsFilter(classInfo);
		
		return classInfo;
		
	}
	/**
	 * 如果有父类的话，加载父类
	 * @param classInfo
	 */
	private void loadSuperClassInfo(ClassFile classFile) {
		String superClass = classFile.getSuper_class();
		if(MethodUtil.isNotObject(superClass)){
			this.loadClass(StringUtil.replacePathToPoint(superClass));
		}
	}

	public abstract ClassFile loadClassFile(String className) throws JvmException ;
	/**
	 * 从ClassFile拷贝到ClassInfo
	 * @param classFile
	 * @return
	 */
	private ClassInfo copyClassFileToClassInfo(ClassFile classFile) {
		//1、拷贝classinfo
		ClassInfo classInfo = new ClassInfo();
		classInfo.setName(StringUtil.replacePathToPoint(classFile.this_class));
		
		//拷贝superClass
		if(MethodUtil.isNotObject(classFile.super_class)){
			ClassInfo superClassInfo = Memory.MethodArea.getClassInfo(StringUtil.replacePathToPoint(classFile.super_class));
			classInfo.setSuperClassInfo(superClassInfo);
		}
		
		//2、拷贝constants
		Map<Integer, ConstantFile> classFile_constants = classFile.getConstantFiles();
		Map<Integer, String> classInfo_constants = new HashMap<Integer, String>();
		Set<Integer> set = classFile_constants.keySet();
		for(Integer key : set){
			classInfo_constants.put(key, classFile_constants.get(key).content);
		}
		classInfo.setConstants(classInfo_constants);
		
		//3、拷贝methodinfo和fieldinfo
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		List<FieldInfo> fields = new ArrayList<FieldInfo>();
		for(FieldMethodFile methodfile : classFile.methods_array){
			if(methodfile.type == 'M'){
				MethodInfo methodinfo = new MethodInfo();
				methodinfo.setClassInfo(classInfo);
				methodinfo.setName(methodfile.name_index);
				methodinfo.setDescriptor(methodfile.descriptor_index);
				//转换instruction
				List<Instruction> instr = mergeByteCode(methodfile,classFile);
				methodinfo.setMethodInstructions(instr);
				methodinfo.setSynchronized(AccessFlagUtil.isSynchronized(methodfile.access_flags));
				methods.add(methodinfo);
			}else{
				//拷贝fieldinfo
				FieldInfo fieldinfo = new FieldInfo();
				fieldinfo.setName(methodfile.name_index);
				fieldinfo.setDescriptor(methodfile.descriptor_index.replace(";", ""));
				//TODO 默认值
				fieldinfo.setDefaultValue(null);
				fields.add(fieldinfo);
			}
		}
		classInfo.setMethods(methods);
		classInfo.setFields(fields);
		//4、把转换好的类，加载到内存中
		Memory.MethodArea.putClassInfo(StringUtil.replacePathToPoint(classFile.this_class), classInfo);
		
		return classInfo;
		
	}

	/**
	 * 合并字节码
	 * @param mf
	 * @param classFile 
	 * @return
	 */
	private List<Instruction> mergeByteCode(FieldMethodFile methodFile, ClassFile classFile) {
		
		CodeAttributeFile code_attribute = null;
		
		for(CommonAttributeFile temp : methodFile.attributes){
			if(temp.attribute_name.equals("Code")){
				code_attribute = (CodeAttributeFile)temp;
			}
		}
		
		List<Instruction> result = new ArrayList<Instruction>();
		
		for(int i=0;i<code_attribute.byteCodes.size();i++){
			ByteCodeMap.ByteCodeDesc byteCodeDesc = ByteCodeMap.get(code_attribute.byteCodes.get(i));
			if(null == byteCodeDesc){
				try {
					throw new JvmException(methodFile.name_index+ "方法中， 指令["+code_attribute.byteCodes.get(i)+"]没有找到对应的描述，请在 ByteCodeMap 中添加。");
				} catch (JvmException e) {
					e.printStackTrace();
				}
			}else if(byteCodeDesc.index_number == 1){//下一个字节是操作数
				String opcodeNum = null;
				if(byteCodeDesc.type.equals("int")){
					opcodeNum = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1)) + "";
				}
				Instruction instr = new Instruction(byteCodeDesc.desc, opcodeNum);
				result.add(instr);
				i = i + 1;
			}else if(byteCodeDesc.index_number == 2){//下两个字节是操作数
				String opcodeNum = null;
				if(byteCodeDesc.type.equals("int")){
					int next_u2_index = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1)+code_attribute.byteCodes.get(i+2));
					opcodeNum = classFile.getUtf8ConstantContentByIndex(next_u2_index);
				}
				Instruction instr = new Instruction(byteCodeDesc.desc, opcodeNum);
				result.add(instr);
				i = i + 2;		
			}else if(byteCodeDesc.index_number == 4){//下四个字节是操作数
				String opcodeNum = null;
				//String opcodeNum2 = null;
				if(byteCodeDesc.type.equals("invokeinterface")){
					int next_u2_index = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1)+code_attribute.byteCodes.get(i+2));
					opcodeNum = classFile.getUtf8ConstantContentByIndex(next_u2_index);
					//opcodeNum2 = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+3)) + "";
				}
				//Instruction instr = new Instruction(byteCodeDesc.desc, opcodeNum + "," + opcodeNum2 + ",0");
				Instruction instr = new Instruction(byteCodeDesc.desc, opcodeNum);
				result.add(instr);
				i = i + 4;
			}else{//没有操作数
				Instruction instr = new Instruction(byteCodeDesc.desc);
				result.add(instr);
			}
		}
		
		LogUtil.println("print.method.instruction", MethodUtil.toStringWithInstructionList(result));
		return result;
	}
	
	/**
	 * 翻译classfile中的字段
	 * @param classFile
	 */
	private void translateClassFile(ClassFile classFile) {
		
		//解析常量 ，该方法已经放到classFile加载阶段
		//translateConstantFile(classFile);
		
		//1、解析方法
		for(FieldMethodFile mf : classFile.methods_array){
			mf.name_index = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(mf.name_index));
			mf.descriptor_index = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(mf.descriptor_index));
		}
		//2、解析classFile中其他字段
		classFile.this_class =  classFile.getUtf8ConstantContentByIndex(Integer.parseInt(classFile.this_class));
		classFile.super_class = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(classFile.super_class));
	}

	public void translateConstantFile(ClassFile classFile) {
		//（1）解析class、String常量
		Set<Integer> set = classFile.constantFiles.keySet();
		for(Integer key : set){
			ConstantFile cf = classFile.constantFiles.get(key);
			if(cf.type.equals(ConstantTypeEnum.classType.getCode()) || cf.type.equals(ConstantTypeEnum.stringType.getCode())){
				cf.content = classFile.getUtf8ConstantContentByIndex(cf.uft8_index);
				cf.translated = true;
			}
		}
		//（2）解析NameAndType类型的常量
		translateConstantWithTwoIndex(classFile,new String[]{ConstantTypeEnum.nameAndType.getCode()},Constants.ConstantLinkSymbol.nameAndType);
		//（3）解析method、field、interfaceMethod类型的常量
		translateConstantWithTwoIndex(classFile,new String[]{ConstantTypeEnum.method.getCode(),ConstantTypeEnum.field.getCode(),ConstantTypeEnum.interfaceMethod.getCode()},Constants.ConstantLinkSymbol.methodAndField);
		//检查constant池是否还存在没翻译的
		checkConstant(classFile);
	}

	private void checkConstant(ClassFile classFile) {
		Set<Integer> set = classFile.constantFiles.keySet();
		for(Integer key : set){
			ConstantFile cf = classFile.constantFiles.get(key);
			if(!cf.translated){
				try {
					throw new JvmException("classFile中常量类型为["+ ConstantTypeEnum.getName(cf.type)+"]还没有翻译");
				} catch (JvmException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 解析有两个索引的常量
	 * @param classFile
	 * @param constantTypes
	 * @param symbol
	 */
	private void translateConstantWithTwoIndex(ClassFile classFile, String[] constantTypes, String symbol) {
		Set<Integer> set = classFile.constantFiles.keySet();
		for(Integer key : set){
			ConstantFile cf = classFile.constantFiles.get(key);
			if(constainType(constantTypes,cf.type)){
				String pre_content = classFile.getUtf8ConstantContentByIndex(cf.pre_uft8_index);
				String last_content = classFile.getUtf8ConstantContentByIndex(cf.last_uft8_index);
				cf.content = pre_content + symbol + last_content;
				cf.translated = true;
			}
		}
	}


	private boolean constainType(String[] constantTypes, String type) {
		for(String str : constantTypes){
			if(str.equals(type)){
				return true;
			}
		}
		return false;
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
