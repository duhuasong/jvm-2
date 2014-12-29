package jvm.classloader.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.AbsClassLoader;
import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classfile.MethodFile;
import jvm.classloader.classstruct.ClassElement;
import jvm.classloader.classstruct.ClassReadCounter;
import jvm.util.ByteHexUtil;
import jvm.util.Constants;
import jvm.util.StringUtil;

public class BaseClassLoader extends AbsClassLoader {

	public ClassFile loadClassFile(String className) {
		
		String classfilepath = getClassfilePath(className);

		FileInputStream fis = null;
		
		ClassFile classFile = new ClassFile();

		try {
			fis = new FileInputStream(new File(classfilepath));
			//从常量池开始读
			ClassReadCounter counter = new ClassReadCounter();
			//下一元素
			ClassElement readElement = counter.getCurElement();
			
			int constant_pool_count = 0;
			int constant_pool_pointer = 1;
			//当前读取的常量类型
			String constant_type = null;
			//当前读取的常量类型对应的片段（utf8对应3，class类型对应2个部分等）
			int constant_type_part = 1;
			//当前读取的方法类型对应的片段（5个部分，access_flags，name_index，descriptor_index，attributes_count，attributes_info）
			int method_info_part = 1;
			//当前读取的属性类型对应的片段
			int attribute_info_part = 1;
			
			
			int len = readElement.size;
			byte[] temp = new byte[len];
			
			while ((fis.read(temp, 0, len)) != -1) {
				
				if(readElement.name.equals("magic")){
					String hex = ByteHexUtil.bytesToHexString(temp);
					classFile.setMagic(hex);
					
				}else if(readElement.name.equals("minor_version")){
					String hex = ByteHexUtil.bytesToHexString(temp);
					classFile.setMinor_version(hex);
					
				}else if(readElement.name.equals("major_version")){
					String hex = ByteHexUtil.bytesToHexString(temp);
					classFile.setMajor_version(hex);
					
				}else if(readElement.name.equals("constant_pool_count")){
					constant_pool_count = ByteHexUtil.getInt(temp, false, temp.length);
					classFile.setConstant_pool_count(constant_pool_count);
					
				}else if(readElement.name.equals("constant_pool_array")){
					
					if(null == constant_type){
						constant_type = ByteHexUtil.bytesToHexString(new byte[]{temp[0]});
					}
					
					if(Constants.ConstantType.ClassType.equals(constant_type)){//如果常量类型是07，class类型
						//如果当前读取的是class常量的第一部分
						if(constant_type_part == 1){
							len = 2;
							temp = new byte[len];
							constant_type_part++;
							continue;
						}else if(constant_type_part == 2){
							int utf8_index = ByteHexUtil.getInt(new byte[]{temp[0],temp[1]}, false, 2);
							ConstantFile cf = new ConstantFile(Constants.ConstantType.ClassType,utf8_index);
							classFile.constantFiles.put(constant_pool_pointer++, cf);
							//如果常量池遍历结束，则执行下一个元素
							if(constant_pool_pointer >= constant_pool_count){
								readElement = counter.getCurElement();
								len = readElement.size;
								temp = new byte[len];
								continue;
							}else{
								//重置变量，读取下一个常量
								constant_type = null;
								constant_type_part = 1;
								len = 1;
								temp = new byte[len];
								continue;
							}
						}
					}else if(Constants.ConstantType.utf8.equals(constant_type)){//如果常量类型是01
						//如果当前读取的是常量的第一部分
						if(constant_type_part == 1){
							len = 2;
							temp = new byte[len];
							constant_type_part++;
							continue;
						}else if(constant_type_part == 2){
							len = ByteHexUtil.getInt(new byte[]{temp[0],temp[1]}, false, 2);
							temp = new byte[len];
							constant_type_part++;
							continue;
						}else if(constant_type_part == 3){
							String content = ByteHexUtil.getStringFromUtf8(temp);
							ConstantFile cf = new ConstantFile(Constants.ConstantType.utf8,content);
							classFile.constantFiles.put(constant_pool_pointer++, cf);
							
							//如果常量池遍历结束，则执行下一个元素
							if(constant_pool_pointer >= constant_pool_count){
								readElement = counter.getCurElement();
								len = readElement.size;
								temp = new byte[len];
								continue;
							}else{
								//重置变量，读取下一个常量
								constant_type = null;
								constant_type_part = 1;
								len = 1;
								temp = new byte[len];
								continue;
							}
						}
					}else if(Constants.ConstantType.method.equals(constant_type) || Constants.ConstantType.field.equals(constant_type) || Constants.ConstantType.nameAndType.equals(constant_type)){//如果常量类型是0a
						//如果当前读取的是常量的第一部分
						if(constant_type_part == 1){
							len = 4;
							temp = new byte[len];
							constant_type_part++;
							continue;
						}else if(constant_type_part == 2){
							int pre_uft8_index = ByteHexUtil.getInt(new byte[]{temp[0],temp[1]}, false, 2);
							int last_uft8_index = ByteHexUtil.getInt(new byte[]{temp[2],temp[3]}, false, 2);
							ConstantFile cf = new ConstantFile(constant_type,pre_uft8_index,last_uft8_index);
							classFile.constantFiles.put(constant_pool_pointer++, cf);
							
							//如果常量池遍历结束，则执行下一个元素
							if(constant_pool_pointer >= constant_pool_count){
								readElement = counter.getCurElement();
								len = readElement.size;
								temp = new byte[len];
								continue;
							}else{
								//重置变量，读取下一个常量
								constant_type = null;
								constant_type_part = 1;
								len = 1;
								temp = new byte[len];
								continue;
							}
							
						}
					}
					
				}else if(readElement.name.equals("access_flags")){
					String  access_flags= ByteHexUtil.bytesToHexString(temp);
					classFile.access_flags = access_flags;
					
				}else if(readElement.name.equals("this_class")){
					int  this_class_index= ByteHexUtil.getInt(temp, false, temp.length);
					classFile.this_class = this_class_index+"";
					
				}else if(readElement.name.equals("super_class")){
					int  super_class_index= ByteHexUtil.getInt(temp, false, temp.length);
					classFile.super_class = super_class_index+"";
					
				}else if(readElement.name.equals("interfaces_count")){
					int  interfaces_count= ByteHexUtil.getInt(temp, false, temp.length);
					classFile.interfaces_count = interfaces_count;
					
					//如果接口数为0，那么接口interfaces_array没有，所以跳过
					if(interfaces_count == 0){
						readElement = counter.getCurElement();
						readElement = counter.getCurElement();
						len = readElement.size;
						temp = new byte[len];
						continue;
					}
					
				}else if(readElement.name.equals("fields_count")){
					int  fields_count= ByteHexUtil.getInt(temp, false, temp.length);
					classFile.fields_count = fields_count;
					
					//如果字段数为0，那么接口interfaces_array没有，所以跳过
					if(fields_count == 0){
						readElement = counter.getCurElement();
						readElement = counter.getCurElement();
						len = readElement.size;
						temp = new byte[len];
						continue;
					}
					
				}else if(readElement.name.equals("methods_count")){
					int  methods_count= ByteHexUtil.getInt(temp, false, temp.length);
					classFile.methods_count = methods_count;
					
				}else if(readElement.name.equals("methods_array")){
					if(method_info_part == 1){
						String  access_flags = ByteHexUtil.bytesToHexString(temp);
						MethodFile mf = new MethodFile();
						mf.access_flags = access_flags;
						classFile.methods_array.add(mf);
						
					}else if(method_info_part == 2){
						int name_index = ByteHexUtil.getInt(temp, false, temp.length);
						MethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.name_index = name_index + "";
						
					}else if(method_info_part == 3){
						int descriptor_index = ByteHexUtil.getInt(temp, false, temp.length);
						MethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.descriptor_index = descriptor_index + "";
						
					}else if(method_info_part == 4){
						int attributes_count = ByteHexUtil.getInt(temp, false, temp.length);
						MethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						mf.attributes_count = attributes_count;
						
					}else if(method_info_part == 5){//说明开始读取属性信息
						MethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
						if(attribute_info_part == 1){
							int attribute_name_index = ByteHexUtil.getInt(temp, false, temp.length);
							String attribute_name = classFile.getUtf8ConstantContentByIndex(attribute_name_index);
							mf.current_attributes_type = attribute_name;
							mf.setAttributeType(attribute_name);
							
							len = 4;
							temp = new byte[len];
							attribute_info_part++;
							continue;
						}else if(attribute_info_part == 2){
							int attribute_length = ByteHexUtil.getInt(temp, false, temp.length);
							mf.setAttributeLength(attribute_length);
							
							len = 2;
							temp = new byte[len];
							attribute_info_part++;
							continue;
						}else if(attribute_info_part == 3){
							int max_stack = ByteHexUtil.getInt(temp, false, temp.length);
							mf.setAttributeMaxStack(max_stack);
							
							len = 2;
							temp = new byte[len];
							attribute_info_part++;
							continue;
						}else if(attribute_info_part == 4){
							int max_locals = ByteHexUtil.getInt(temp, false, temp.length);
							mf.setAttributeMaxLocals(max_locals);
							
							len = 4;
							temp = new byte[len];
							attribute_info_part++;
							continue;
						}else if(attribute_info_part == 5){
							int code_length = ByteHexUtil.getInt(temp, false, temp.length);
							mf.setAttributeCodeLength(code_length);
							
							len = 1;
							temp = new byte[len];
							attribute_info_part++;
							continue;
						}else if(attribute_info_part == 6){//开始读取指令，一个指令一个字节
							if(mf.isLastByteCode()){
								String byteCode = ByteHexUtil.bytesToHexString(temp);
								mf.setAttributeByteCode(byteCode);
								
								//一次读完该属性剩余的字节
								len = mf.getAttributeRemainBytes();
								temp = new byte[len];
								attribute_info_part++;
								continue;
								
							}else{
								String byteCode = ByteHexUtil.bytesToHexString(temp);
								mf.setAttributeByteCode(byteCode);
								
								len = 1;
								temp = new byte[len];
								continue;
							}
						}else if(attribute_info_part == 7){
								//TODO 读取剩余的字节 目前不需要
							
								if(mf.hasRemainAttrs()){//此方法还有属性
									attribute_info_part = 1;
									len = 2;
									temp = new byte[len];
									continue;
								}else if(classFile.hasRemainMethods()){//还有下一个方法
									attribute_info_part = 1;
									method_info_part = 1;
									len = 2;
									temp = new byte[len];
									continue;
								}else{
									break;
								}
						}
					}
					
					//-------------默认读取2个字节，并且method_info_part++
					len = 2;
					temp = new byte[len];
					method_info_part++;
					continue;
				}
				
				//--------每个分支默认设置readElement、len和temp----------
				readElement = counter.getCurElement();
				len = readElement.size;
				temp = new byte[len];
				
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
