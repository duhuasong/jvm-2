package jvm.classloader;

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
import jvm.classloader.help.ByteCodeMap;
import jvm.engine.instruction.Instruction;
import jvm.memory.Memory;
import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.FieldInfo;
import jvm.memory.classinfo.MethodInfo;
import jvm.util.Constants;
import jvm.util.MethodUtil;
import jvm.util.common.ByteHexUtil;
import jvm.util.common.LogUtil;
import jvm.util.common.StringUtil;

public abstract class AbstractClassLoader implements InterfaceClassLoader {

	@Override
	public ClassInfo loadClass(String className) {
		
		ClassFile classFile = loadClassFile(className);
		
		translateClassFile(classFile);
		
		LogUtil.println("print.classfile", classFile.toString());
		
		ClassInfo classInfo = copyClassFileToClassInfo(classFile);
		
		LogUtil.println("print.classinfo", classInfo.toString());
		
		entrancesMethodsFilter(classInfo);
		
		return classInfo;
		
	}
	
	public abstract ClassFile loadClassFile(String className) ;
	/**
	 * ��ClassFile������ClassInfo
	 * @param classFile
	 * @return
	 */
	private ClassInfo copyClassFileToClassInfo(ClassFile classFile) {
		//1������classinfo
		ClassInfo classInfo = new ClassInfo();
		classInfo.setName(StringUtil.replacePathToPoint(classFile.this_class));
		
		//2������constants
		Map<Integer, ConstantFile> classFile_constants = classFile.getConstantFiles();
		Map<Integer, String> classInfo_constants = new HashMap<Integer, String>();
		Set<Integer> set = classFile_constants.keySet();
		for(Integer key : set){
			classInfo_constants.put(key, classFile_constants.get(key).content);
		}
		classInfo.setConstants(classInfo_constants);
		
		//3������methodinfo��fieldinfo
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		List<FieldInfo> fields = new ArrayList<FieldInfo>();
		for(FieldMethodFile methodfile : classFile.methods_array){
			if(methodfile.type == 'M'){
				MethodInfo methodinfo = new MethodInfo();
				methodinfo.setClassInfo(classInfo);
				methodinfo.setName(methodfile.name_index);
				methodinfo.setDescriptor(methodfile.descriptor_index);
				//ת��instruction
				List<Instruction> instr = mergeByteCode(methodfile,classFile);
				methodinfo.setMethodInstructions(instr);
				methods.add(methodinfo);
			}else{
				//����fieldinfo
				FieldInfo fieldinfo = new FieldInfo();
				fieldinfo.setName(methodfile.name_index);
				fieldinfo.setDescriptor(methodfile.descriptor_index);
				//TODO Ĭ��ֵ
				fieldinfo.setDefaultValue(null);
				fields.add(fieldinfo);
			}
		}
		classInfo.setMethods(methods);
		classInfo.setFields(fields);
		//4����ת���õ��࣬���ص��ڴ���
		Memory.MethodArea.putClassInfo(StringUtil.replacePathToPoint(classFile.this_class), classInfo);
		
		return classInfo;
		
	}

	/**
	 * �ϲ��ֽ���
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
				System.err.println(methodFile.name_index+ "�����У� ָ��["+code_attribute.byteCodes.get(i)+"]û���ҵ���Ӧ������");
			}else if(byteCodeDesc.index_number == 2){//�������ֽ��ǲ�����
				int next_u2_index = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1)+code_attribute.byteCodes.get(i+2));
				String opcodeNum = classFile.getUtf8ConstantContentByIndex(next_u2_index);
				Instruction instr = new Instruction(byteCodeDesc.desc, opcodeNum);
				result.add(instr);
				i = i + 2;		
			}else if(byteCodeDesc.index_number == 1){//��һ���ֽ��ǲ�����
				int next_u1 = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1));
				Instruction instr = new Instruction(byteCodeDesc.desc, next_u1);
				result.add(instr);
				i = i + 1;
			}else{//û�в�����
				Instruction instr = new Instruction(byteCodeDesc.desc);
				result.add(instr);
			}
		}
		
		LogUtil.println("print.method.instruction", MethodUtil.toStringWithInstructionList(result));
		return result;
	}
	
	/**
	 * ����classfile�е��ֶ�
	 * @param classFile
	 */
	private void translateClassFile(ClassFile classFile) {
		
		//1���������� ���÷����Ѿ��ŵ�classFile���ؽ׶�
		//translateConstantFile(classFile);
		
		//2����������
		for(FieldMethodFile mf : classFile.methods_array){
			mf.name_index = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(mf.name_index));
			mf.descriptor_index = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(mf.descriptor_index));
		}
		//3������classFile�������ֶ�
		classFile.this_class =  classFile.getUtf8ConstantContentByIndex(Integer.parseInt(classFile.this_class));
		
	}

	public void translateConstantFile(ClassFile classFile) {
		//��1������class��String����
		Set<Integer> set = classFile.constantFiles.keySet();
		for(Integer key : set){
			ConstantFile cf = classFile.constantFiles.get(key);
			if(cf.type.equals(Constants.ConstantType.classType) || cf.type.equals(Constants.ConstantType.stringType)){
				cf.content = classFile.getUtf8ConstantContentByIndex(cf.uft8_index);
			}
		}
		//��2������NameAndType���͵ĳ���
		translateConstantWithTwoIndex(classFile,new String[]{Constants.ConstantType.nameAndType},Constants.ConstantLinkSymbol.nameAndType);
		//��3������method��field���͵ĳ���
		translateConstantWithTwoIndex(classFile,new String[]{Constants.ConstantType.method,Constants.ConstantType.field},Constants.ConstantLinkSymbol.methodAndField);
		
	}

	/**
	 * ���������������ĳ���
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
