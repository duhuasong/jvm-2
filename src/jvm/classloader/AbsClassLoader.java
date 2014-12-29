package jvm.classloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.CodeAttributeFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classfile.MethodFile;
import jvm.engine.instruction.Instruction;
import jvm.memory.Memory;
import jvm.memory.classinfo.ClassInfo;
import jvm.memory.classinfo.MethodInfo;
import jvm.util.ByteHexUtil;
import jvm.util.Constants;
import jvm.util.LogUtil;
import jvm.util.MethodUtil;
import jvm.util.StringUtil;

public abstract class AbsClassLoader implements IClassLoader {

	@Override
	public void loadClass(String className) {
		
		ClassFile classFile = loadClassFile(className);
		
		translateClassFile(classFile);
		
		LogUtil.println("print.classfile", classFile.toString());
		
		ClassInfo classInfo = copyClassFileToClassInfo(classFile);
		
		LogUtil.println("print.classinfo", classInfo.toString());
		
		entrancesMethodsFilter(classInfo);
		
	}
	
	public abstract ClassFile loadClassFile(String className) ;

	private ClassInfo copyClassFileToClassInfo(ClassFile classFile) {
		//1������classinfo
		ClassInfo classInfo = new ClassInfo();
		classInfo.setName(StringUtil.replacePathToClass(classFile.this_class));
		
		//2������methodinfo
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		for(MethodFile methodfile : classFile.methods_array){
			MethodInfo methodinfo = new MethodInfo();
			methodinfo.setClassInfo(classInfo);
			methodinfo.setName(methodfile.name_index);
			methodinfo.setDescriptor(methodfile.descriptor_index);
			//ת��instruction
			List<Instruction> instr = mergeByteCode(methodfile,classFile);
			methodinfo.setMethodInstructions(instr);
			methods.add(methodinfo);
		}
		classInfo.setMethods(methods);
		//3����ת���õ��࣬���ص��ڴ���
		Memory.classPool.put(StringUtil.replacePathToClass(classFile.this_class), classInfo);
		
		return classInfo;
		
	}

	/**
	 * �ϲ��ֽ���
	 * @param mf
	 * @param classFile 
	 * @return
	 */
	private List<Instruction> mergeByteCode(MethodFile methodFile, ClassFile classFile) {
		
		CodeAttributeFile code_attribute = null;
		
		for(CodeAttributeFile temp : methodFile.code_attributes){
			if(temp.attribute_name.equals("Code")){
				code_attribute = temp;
			}
		}
		
		List<Instruction> result = new ArrayList<Instruction>();
		
		for(int i=0;i<code_attribute.byteCodes.size();i++){
			String opcode = Constants.InstructionMap.get(code_attribute.byteCodes.get(i));
			if(null == opcode){
				System.err.println(methodFile.name_index+ "�����У� ָ��["+code_attribute.byteCodes.get(i)+"]û���ҵ���Ӧ������");
			}
			if("invokestatic".equals(opcode) || "getstatic".equals(opcode) || "invokevirtual".equals(opcode) ){//�������ֽ��ǲ�����
				int next_u2_index = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1)+code_attribute.byteCodes.get(i+2));
				String opcodeNum = classFile.getUtf8ConstantContentByIndex(next_u2_index);
				Instruction instr = new Instruction(opcode, opcodeNum);
				result.add(instr);
				i = i + 2;		
			}else if("bipush".equals(opcode)){//��һ���ֽ��ǲ�����
				int next_u1 = ByteHexUtil.fromHexToInt(code_attribute.byteCodes.get(i+1));
				Instruction instr = new Instruction(opcode, next_u1);
				result.add(instr);
				i = i + 1;
			}else{//û�в�����
				Instruction instr = new Instruction(opcode);
				result.add(instr);
			}
		}
		
		return result;
	}
	
	/**
	 * ����classfile�е��ֶ�
	 * @param classFile
	 */
	private void translateClassFile(ClassFile classFile) {
		
		//1����������
		Set<Integer> set = classFile.constantFiles.keySet();
		//��1������class����
		for(Integer key : set){
			ConstantFile cf = classFile.constantFiles.get(key);
			if(cf.type.equals(Constants.ConstantType.ClassType)){
				cf.content = classFile.getUtf8ConstantContentByIndex(cf.uft8_index);
			}
		}
		//��2������NameAndType���͵ĳ���
		translateConstantWithTwoIndex(classFile,new String[]{Constants.ConstantType.nameAndType},Constants.ConstantLinkSymbol.nameAndType);
		//��3������method��field���͵ĳ���
		translateConstantWithTwoIndex(classFile,new String[]{Constants.ConstantType.method,Constants.ConstantType.field},Constants.ConstantLinkSymbol.methodAndField);
		
		//2����������
		for(MethodFile mf : classFile.methods_array){
			mf.name_index = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(mf.name_index));
			mf.descriptor_index = classFile.getUtf8ConstantContentByIndex(Integer.parseInt(mf.descriptor_index));
		}
		//3������classFile�������ֶ�
		classFile.this_class =  classFile.getUtf8ConstantContentByIndex(Integer.parseInt(classFile.this_class));
		
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
