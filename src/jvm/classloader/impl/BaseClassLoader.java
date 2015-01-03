package jvm.classloader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.AbstractClassLoader;
import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classfile.FieldMethodFile;
import jvm.classloader.help.ClassFileReadCounter;
import jvm.classloader.help.TempVariable;
import jvm.classloader.help.ClassFileReadCounter.ClassElement;
import jvm.util.Constants;
import jvm.util.common.ByteHexUtil;
import jvm.util.common.LogUtil;
import jvm.util.common.StringUtil;

public class BaseClassLoader extends AbstractClassLoader {

	public ClassFile loadClassFile(String className) {
		
		String classfilepath = getClassfilePath(className);

		FileInputStream fis = null;
		
		ClassFile classFile = new ClassFile();

		try {
			fis = new FileInputStream(new File(classfilepath));
			//�ӳ����ؿ�ʼ��
			ClassFileReadCounter counter = new ClassFileReadCounter();
			//��һԪ��
			ClassElement readElement = counter.getCurElement();
			
			TempVariable obj = new TempVariable();
			obj.len = readElement.size;
			obj.temp = new byte[obj.len];
			
			while ((fis.read(obj.temp, 0, obj.len)) != -1) {
				
				if(readElement.name.equals("magic")){
					String hex = ByteHexUtil.bytesToHexString(obj.temp);
					classFile.setMagic(hex);
					
				}else if(readElement.name.equals("minor_version")){
					String hex = ByteHexUtil.bytesToHexString(obj.temp);
					classFile.setMinor_version(hex);
					
				}else if(readElement.name.equals("major_version")){
					String hex = ByteHexUtil.bytesToHexString(obj.temp);
					classFile.setMajor_version(hex);
					
				}else if(readElement.name.equals("constant_pool_count")){
					obj.constant_pool_count = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.setConstant_pool_count(obj.constant_pool_count);
					
				}else if(readElement.name.equals("constant_pool_array")){
					
					if(null == obj.constant_type){
						obj.constant_type = ByteHexUtil.bytesToHexString(new byte[]{obj.temp[0]});
					}
					
					if(Constants.ConstantType.classType.equals(obj.constant_type) || Constants.ConstantType.stringType.equals(obj.constant_type) ){//�������������07��class����
						//�����ǰ��ȡ����class��string�����ĵ�һ����
						if(obj.constant_type_part == 1){
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							int utf8_index = ByteHexUtil.getInt(new byte[]{obj.temp[0],obj.temp[1]}, false, 2);
							ConstantFile cf = new ConstantFile(obj.constant_type,utf8_index);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							//��������ر�����������ִ����һ��Ԫ��
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								obj.len = readElement.size;
								obj.temp = new byte[obj.len];
								continue;
							}else{
								//���ñ�������ȡ��һ������
								obj.constant_type = null;
								obj.constant_type_part = 1;
								obj.len = 1;
								obj.temp = new byte[obj.len];
								continue;
							}
						}
					}else if(Constants.ConstantType.utf8.equals(obj.constant_type)){//�������������01
						//�����ǰ��ȡ���ǳ����ĵ�һ����
						if(obj.constant_type_part == 1){
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							obj.len = ByteHexUtil.getInt(new byte[]{obj.temp[0],obj.temp[1]}, false, 2);
							obj.temp = new byte[obj.len];
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 3){
							String content = ByteHexUtil.getStringFromUtf8(obj.temp);
							ConstantFile cf = new ConstantFile(Constants.ConstantType.utf8,content);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							
							//��������ر�����������ִ����һ��Ԫ��
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								obj.len = readElement.size;
								obj.temp = new byte[obj.len];
								continue;
							}else{
								//���ñ�������ȡ��һ������
								obj.constant_type = null;
								obj.constant_type_part = 1;
								obj.len = 1;
								obj.temp = new byte[obj.len];
								continue;
							}
						}
					}else if(Constants.ConstantType.method.equals(obj.constant_type) || Constants.ConstantType.field.equals(obj.constant_type) || Constants.ConstantType.nameAndType.equals(obj.constant_type)){//�������������0a
						//�����ǰ��ȡ���ǳ����ĵ�һ����
						if(obj.constant_type_part == 1){
							obj.len = 4;
							obj.temp = new byte[obj.len];
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							int pre_uft8_index = ByteHexUtil.getInt(new byte[]{obj.temp[0],obj.temp[1]}, false, 2);
							int last_uft8_index = ByteHexUtil.getInt(new byte[]{obj.temp[2],obj.temp[3]}, false, 2);
							ConstantFile cf = new ConstantFile(obj.constant_type,pre_uft8_index,last_uft8_index);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							
							//��������ر�����������ִ����һ��Ԫ��
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								obj.len = readElement.size;
								obj.temp = new byte[obj.len];
								continue;
							}else{
								//���ñ�������ȡ��һ������
								obj.constant_type = null;
								obj.constant_type_part = 1;
								obj.len = 1;
								obj.temp = new byte[obj.len];
								continue;
							}
							
						}
					}
					
				}else if(readElement.name.equals("access_flags")){
					LogUtil.println("print.classfile.constant_pool_array", classFile.constantToString());
					
					String  access_flags= ByteHexUtil.bytesToHexString(obj.temp);
					classFile.access_flags = access_flags;
					
				}else if(readElement.name.equals("this_class")){
					int  this_class_index= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.this_class = this_class_index+"";
					
				}else if(readElement.name.equals("super_class")){
					int  super_class_index= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.super_class = super_class_index+"";
					
				}else if(readElement.name.equals("interfaces_count")){
					int  interfaces_count= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.interfaces_count = interfaces_count;
					
					//����ӿ���Ϊ0����ô�ӿ�interfaces_arrayû�У���������
					if(interfaces_count == 0){
						readElement = counter.getCurElement();
						readElement = counter.getCurElement();
						obj.len = readElement.size;
						obj.temp = new byte[obj.len];
						continue;
					}
					
				}else if(readElement.name.equals("fields_count")){
					int  fields_count= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.fields_count = fields_count;
					
					//����ֶ���Ϊ0����ô�ӿ�fields_arrayû�У���������
					if(fields_count == 0){
						readElement = counter.getCurElement();
						readElement = counter.getCurElement();
						obj.len = readElement.size;
						obj.temp = new byte[obj.len];
						continue;
					}
					
				}else if(readElement.name.equals("fields_array")){
					if(obj.field_or_method_info_part == 1){
						String  access_flags = ByteHexUtil.bytesToHexString(obj.temp);
						FieldMethodFile mf = new FieldMethodFile();
						mf.access_flags = access_flags;
						classFile.methods_array.add(mf);
						
					}else if(obj.field_or_method_info_part == 2){
						int name_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.name_index = name_index + "";
						
					}else if(obj.field_or_method_info_part == 3){
						int descriptor_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.descriptor_index = descriptor_index + "";
						
					}else if(obj.field_or_method_info_part == 4){
						int attributes_count = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.attributes_count = attributes_count;
						
					}else if(obj.field_or_method_info_part == 5){//˵����ʼ��ȡ������Ϣ
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						if(obj.attribute_info_part == 1){
							int attribute_name_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							String attribute_name = classFile.getUtf8ConstantContentByIndex(attribute_name_index);
							mf.current_attributes_type = attribute_name;
							mf.setAttributeName(attribute_name);
							
							obj.len = 4;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 2){
							int attribute_length = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							mf.setAttributeLength(attribute_length);
							
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 3){
							int max_stack = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							mf.setAttributeMaxStack(max_stack);
							
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}
					}
				}else if(readElement.name.equals("methods_count")){
					int  methods_count= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.methods_count = methods_count;
					
				}else if(readElement.name.equals("methods_array")){
					if(obj.field_or_method_info_part == 1){
						String  access_flags = ByteHexUtil.bytesToHexString(obj.temp);
						FieldMethodFile mf = new FieldMethodFile();
						mf.access_flags = access_flags;
						classFile.methods_array.add(mf);
						
					}else if(obj.field_or_method_info_part == 2){
						int name_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.name_index = name_index + "";
						
					}else if(obj.field_or_method_info_part == 3){
						int descriptor_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.descriptor_index = descriptor_index + "";
						
					}else if(obj.field_or_method_info_part == 4){
						int attributes_count = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.attributes_count = attributes_count;
						
					}else if(obj.field_or_method_info_part == 5){//˵����ʼ��ȡ������Ϣ
						FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						if(obj.attribute_info_part == 1){
							int attribute_name_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							String attribute_name = classFile.getUtf8ConstantContentByIndex(attribute_name_index);
							mf.current_attributes_type = attribute_name;
							mf.setAttributeName(attribute_name);
							
							obj.len = 4;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 2){
							int attribute_length = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							mf.setAttributeLength(attribute_length);
							
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 3){
							int max_stack = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							mf.setAttributeMaxStack(max_stack);
							
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 4){
							int max_locals = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							mf.setAttributeMaxLocals(max_locals);
							
							obj.len = 4;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 5){
							int code_length = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
							mf.setAttributeCodeLength(code_length);
							
							obj.len = 1;
							obj.temp = new byte[obj.len];
							obj.attribute_info_part++;
							continue;
						}else if(obj.attribute_info_part == 6){//��ʼ��ȡָ�һ��ָ��һ���ֽ�
							if(mf.isLastByteCode()){
								String byteCode = ByteHexUtil.bytesToHexString(obj.temp);
								mf.setAttributeByteCode(byteCode);
								
								//һ�ζ��������ʣ����ֽ�
								obj.len = mf.getAttributeRemainBytes();
								obj.temp = new byte[obj.len];
								obj.attribute_info_part++;
								continue;
								
							}else{
								String byteCode = ByteHexUtil.bytesToHexString(obj.temp);
								mf.setAttributeByteCode(byteCode);
								
								obj.len = 1;
								obj.temp = new byte[obj.len];
								continue;
							}
						}else if(obj.attribute_info_part == 7){
								//TODO ��ȡʣ����ֽ� Ŀǰ����Ҫ
							
								if(mf.hasRemainAttrs()){//�˷�����������
									obj.attribute_info_part = 1;
									obj.len = 2;
									obj.temp = new byte[obj.len];
									continue;
								}else if(classFile.hasRemainMethods()){//������һ������
									obj.attribute_info_part = 1;
									obj.field_or_method_info_part = 1;
									obj.len = 2;
									obj.temp = new byte[obj.len];
									continue;
								}else{
									break;
								}
						}
					}
					
					//-------------Ĭ�϶�ȡ2���ֽڣ�����tempVariable.method_info_part++
					obj.len = 2;
					obj.temp = new byte[obj.len];
					obj.field_or_method_info_part++;
					continue;
				}
				
				//--------ÿ����֧Ĭ������readElement��len��temp----------
				readElement = counter.getCurElement();
				obj.len = readElement.size;
				obj.temp = new byte[obj.len];
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {     
                if(fis != null){  
                	fis.close(); 
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }  
		}
		return classFile;
	}

	private String getClassfilePath(String className) {
		//��ǰ���̵�class��·�����磺D:\workspaces\myeclipse\wtms3\ztest\bin
		String classpath = System.getProperty("java.class.path");

		String subpath = StringUtil.replaceClassToPath(className);

		String filepath = classpath + "/" + subpath + ".class";
		
		return filepath;
	}
	
	
}
