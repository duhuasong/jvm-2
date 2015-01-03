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
			//从常量池开始读
			ClassFileReadCounter counter = new ClassFileReadCounter();
			//下一元素
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
					
					if(Constants.ConstantType.classType.equals(obj.constant_type) || Constants.ConstantType.stringType.equals(obj.constant_type) ){//如果常量类型是07，class类型
						//如果当前读取的是class、string常量的第一部分
						if(obj.constant_type_part == 1){
							obj.len = 2;
							obj.temp = new byte[obj.len];
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							int utf8_index = ByteHexUtil.getInt(new byte[]{obj.temp[0],obj.temp[1]}, false, 2);
							ConstantFile cf = new ConstantFile(obj.constant_type,utf8_index);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							//如果常量池遍历结束，则执行下一个元素
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								obj.len = readElement.size;
								obj.temp = new byte[obj.len];
								continue;
							}else{
								//重置变量，读取下一个常量
								obj.constant_type = null;
								obj.constant_type_part = 1;
								obj.len = 1;
								obj.temp = new byte[obj.len];
								continue;
							}
						}
					}else if(Constants.ConstantType.utf8.equals(obj.constant_type)){//如果常量类型是01
						//如果当前读取的是常量的第一部分
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
							
							//如果常量池遍历结束，则执行下一个元素
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								obj.len = readElement.size;
								obj.temp = new byte[obj.len];
								continue;
							}else{
								//重置变量，读取下一个常量
								obj.constant_type = null;
								obj.constant_type_part = 1;
								obj.len = 1;
								obj.temp = new byte[obj.len];
								continue;
							}
						}
					}else if(Constants.ConstantType.method.equals(obj.constant_type) || Constants.ConstantType.field.equals(obj.constant_type) || Constants.ConstantType.nameAndType.equals(obj.constant_type)){//如果常量类型是0a
						//如果当前读取的是常量的第一部分
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
							
							//如果常量池遍历结束，则执行下一个元素
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								obj.len = readElement.size;
								obj.temp = new byte[obj.len];
								continue;
							}else{
								//重置变量，读取下一个常量
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
					
					//如果接口数为0，那么接口interfaces_array没有，所以跳过
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
					
					//如果字段数为0，那么接口fields_array没有，所以跳过
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
						
					}else if(obj.field_or_method_info_part == 5){//说明开始读取属性信息
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
						
					}else if(obj.field_or_method_info_part == 5){//说明开始读取属性信息
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
						}else if(obj.attribute_info_part == 6){//开始读取指令，一个指令一个字节
							if(mf.isLastByteCode()){
								String byteCode = ByteHexUtil.bytesToHexString(obj.temp);
								mf.setAttributeByteCode(byteCode);
								
								//一次读完该属性剩余的字节
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
								//TODO 读取剩余的字节 目前不需要
							
								if(mf.hasRemainAttrs()){//此方法还有属性
									obj.attribute_info_part = 1;
									obj.len = 2;
									obj.temp = new byte[obj.len];
									continue;
								}else if(classFile.hasRemainMethods()){//还有下一个方法
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
					
					//-------------默认读取2个字节，并且tempVariable.method_info_part++
					obj.len = 2;
					obj.temp = new byte[obj.len];
					obj.field_or_method_info_part++;
					continue;
				}
				
				//--------每个分支默认设置readElement、len和temp----------
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
		//当前工程的class根路径，如：D:\workspaces\myeclipse\wtms3\ztest\bin
		String classpath = System.getProperty("java.class.path");

		String subpath = StringUtil.replaceClassToPath(className);

		String filepath = classpath + "/" + subpath + ".class";
		
		return filepath;
	}
	
	
}
