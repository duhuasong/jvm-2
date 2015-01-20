package jvm.classloader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.AbstractClassLoader;
import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classfile.FieldMethodFile;
import jvm.classloader.tool.ClassFileReadCounter;
import jvm.classloader.tool.TempVariable;
import jvm.classloader.tool.ClassFileReadCounter.ClassElement;
import jvm.util.Constants;
import jvm.util.common.ByteHexUtil;
import jvm.util.common.LogUtil;
import jvm.util.common.StringUtil;
import jvm.util.enums.ConstantTypeEnum;
import jvm.util.exception.JvmException;

public class BaseClassLoader extends AbstractClassLoader {

	public ClassFile loadClassFile(String className) throws JvmException {
		
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
			setLenAndTemp(obj,readElement.size);
			
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
						LogUtil.println("print.classfile.every_pool", "*******开始读取第["+obj.constant_pool_pointer+"]个常量，类型为["+ConstantTypeEnum.getName(obj.constant_type)+"]");
					}
					if(ConstantTypeEnum.classType.getCode().equals(obj.constant_type) || ConstantTypeEnum.stringType.getCode().equals(obj.constant_type) ){//如果常量类型是07，class类型
						//如果当前读取的是class、string常量的第一部分
						if(obj.constant_type_part == 1){
							setLenAndTemp(obj,2);
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							int utf8_index = ByteHexUtil.getInt(new byte[]{obj.temp[0],obj.temp[1]}, false, 2);
							ConstantFile cf = new ConstantFile(obj.constant_type,utf8_index);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							//如果常量池遍历结束，则执行下一个元素
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								setLenAndTemp(obj,readElement.size);
								continue;
							}else{
								//重置变量，读取下一个常量
								obj.constant_type = null;
								obj.constant_type_part = 1;
								setLenAndTemp(obj,1);
								continue;
							}
						}
					}else if(ConstantTypeEnum.utf8.getCode().equals(obj.constant_type)){//如果常量类型是01
						//如果当前读取的是常量的第一部分
						if(obj.constant_type_part == 1){
							setLenAndTemp(obj,2);
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							obj.len = ByteHexUtil.getInt(new byte[]{obj.temp[0],obj.temp[1]}, false, 2);
							obj.temp = new byte[obj.len];
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 3){
							String content = ByteHexUtil.getStringFromUtf8(obj.temp);
							ConstantFile cf = new ConstantFile(ConstantTypeEnum.utf8.getCode(),content);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							
							//如果常量池遍历结束，则执行下一个元素
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								setLenAndTemp(obj,readElement.size);
								continue;
							}else{
								//重置变量，读取下一个常量
								obj.constant_type = null;
								obj.constant_type_part = 1;
								setLenAndTemp(obj,1);
								continue;
							}
						}
					}else if(ConstantTypeEnum.method.getCode().equals(obj.constant_type) || ConstantTypeEnum.field.getCode().equals(obj.constant_type) || ConstantTypeEnum.nameAndType.getCode().equals(obj.constant_type)){//如果常量类型是0a
						//如果当前读取的是常量的第一部分
						if(obj.constant_type_part == 1){
							setLenAndTemp(obj,4);
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
								setLenAndTemp(obj,readElement.size);
								continue;
							}else{
								//重置变量，读取下一个常量
								obj.constant_type = null;
								obj.constant_type_part = 1;
								setLenAndTemp(obj,1);
								continue;
							}
							
						}
					}else if(ConstantTypeEnum.longType.getCode().equals(obj.constant_type)){
						//如果当前读取的是常量的第一部分
						if(obj.constant_type_part == 1){
							setLenAndTemp(obj,8);
							obj.constant_type_part++;
							continue;
						}else if(obj.constant_type_part == 2){
							long value = ByteHexUtil.byteArrayToInt(obj.temp);
							ConstantFile cf = new ConstantFile(obj.constant_type,""+value);
							classFile.constantFiles.put(obj.constant_pool_pointer++, cf);
							obj.constant_pool_pointer++;//long类型占用两个索引
							
							//如果常量池遍历结束，则执行下一个元素
							if(obj.constant_pool_pointer >= obj.constant_pool_count){
								readElement = counter.getCurElement();
								setLenAndTemp(obj,readElement.size);
								continue;
							}else{
								//重置变量，读取下一个常量
								obj.constant_type = null;
								obj.constant_type_part = 1;
								setLenAndTemp(obj,1);
								continue;
							}
							
						}
					}else{
						throw new JvmException("第["+obj.constant_pool_pointer+"]个常量的类型["+obj.constant_type+"]的读取还未处理。");
					}
					
				}else if(readElement.name.equals("access_flags")){
					//解析常量
					translateConstantFile(classFile);
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
						setLenAndTemp(obj,readElement.size);
						continue;
					}
					
				}else if(readElement.name.equals("fields_count")){
					int  fields_count= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.fields_count = fields_count;
					
					//如果字段数为0，那么接口fields_array没有，所以跳过
					if(fields_count == 0){
						readElement = counter.getCurElement();
						readElement = counter.getCurElement();
						setLenAndTemp(obj,readElement.size);
						continue;
					}
					
				}else if(readElement.name.equals("methods_count")){
					int  methods_count= ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
					classFile.methods_count = methods_count;
					
				}else if(readElement.name.equals("methods_array") || readElement.name.equals("fields_array")){
					String result = loadFieldMethodFile(readElement,counter,obj,classFile);
					if("continue".equals(result)){
						continue;
					}else if("break".equals(result)){
						break;
					}
				}
				
				//--------每个分支默认设置readElement、len和temp----------
				readElement = counter.getCurElement();
				setLenAndTemp(obj,readElement.size);
				
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


	/**
	 * 加载field和method元素
	 * @param readElement
	 * @param counter
	 * @param obj
	 * @param classFile
	 * @return
	 */
	private String loadFieldMethodFile(ClassElement readElement,
			ClassFileReadCounter counter, TempVariable obj, ClassFile classFile) {
		
		if(obj.field_or_method_info_part == 1){
			String  access_flags = ByteHexUtil.bytesToHexString(obj.temp);
			FieldMethodFile mf = new FieldMethodFile();
			if(readElement.name.equals("fields_array")){
				mf.type = 'F';
			}
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
			
			//如果attributes_count为0，判断当前field、method是否读完
			if(mf.attributes_count == 0){
				//如果没有读完，开始读取下一个field或method
				if((readElement.name.equals("fields_array") && classFile.hasRemainFields()) || (readElement.name.equals("methods_array") && classFile.hasRemainMethods()) ){
					obj.attribute_info_part = 1;
					obj.field_or_method_info_part = 1;
					setLenAndTemp(obj,2);
					return "continue";
				}else{
					obj.attribute_info_part = 1;
					obj.field_or_method_info_part = 1;
					setNextElement(readElement,counter);
					setLenAndTemp(obj,readElement.size);
					return "continue";
				}
			}else{
				//TODO 开始读取字段属性
				
			}
			
		}else if(obj.field_or_method_info_part == 5){//说明开始读取属性信息
			FieldMethodFile mf = classFile.methods_array.get(classFile.methods_array.size()-1);
			if(obj.attribute_info_part == 1){
				int attribute_name_index = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
				String attribute_name = classFile.getUtf8ConstantContentByIndex(attribute_name_index);
				mf.current_attributes_type = attribute_name;
				mf.setAttributeName(attribute_name);
				
				setLenAndTemp(obj,4);
				obj.attribute_info_part++;
				return "continue";
			}else if(obj.attribute_info_part == 2){
				int attribute_length = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
				mf.setAttributeLength(attribute_length);
				
				setLenAndTemp(obj,2);
				obj.attribute_info_part++;
				return "continue";
			}else if(obj.attribute_info_part == 3){
				int max_stack = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
				mf.setAttributeMaxStack(max_stack);
				
				setLenAndTemp(obj,2);
				obj.attribute_info_part++;
				return "continue";
			}else if(obj.attribute_info_part == 4){
				int max_locals = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
				mf.setAttributeMaxLocals(max_locals);
				
				setLenAndTemp(obj,4);
				obj.attribute_info_part++;
				return "continue";
			}else if(obj.attribute_info_part == 5){
				int code_length = ByteHexUtil.getInt(obj.temp, false, obj.temp.length);
				mf.setAttributeCodeLength(code_length);
				
				setLenAndTemp(obj,1);
				obj.attribute_info_part++;
				return "continue";
			}else if(obj.attribute_info_part == 6){//开始读取指令，一个指令一个字节
				if(mf.isLastByteCode()){
					String byteCode = ByteHexUtil.bytesToHexString(obj.temp);
					mf.setAttributeByteCode(byteCode);
					
					//一次读完该属性剩余的字节
					obj.len = mf.getAttributeRemainBytes();
					obj.temp = new byte[obj.len];
					obj.attribute_info_part++;
					return "continue";
					
				}else{
					String byteCode = ByteHexUtil.bytesToHexString(obj.temp);
					mf.setAttributeByteCode(byteCode);
					
					setLenAndTemp(obj,1);
					return "continue";
				}
			}else if(obj.attribute_info_part == 7){
					//TODO 读取剩余的字节 目前不需要
				
					if(mf.hasRemainAttrs()){//此方法还有属性
						obj.attribute_info_part = 1;
						setLenAndTemp(obj,2);
						return "continue";
					}else if(classFile.hasRemainMethods()){//还有下一个方法
						obj.attribute_info_part = 1;
						obj.field_or_method_info_part = 1;
						setLenAndTemp(obj,2);
						return "continue";
					}else{
						return "break";
					}
			}
		}
		
		//-------------默认读取2个字节，并且obj.method_info_part++
		setLenAndTemp(obj,2);
		obj.field_or_method_info_part++;
		return "continue";
	}
	/**
	 * 设置当前节点的值为下一个节点
	 * @param readElement
	 * @param counter
	 */
	private void setNextElement(ClassElement readElement,
			ClassFileReadCounter counter) {
		ClassElement nextElement = counter.getCurElement();
		readElement.name = nextElement.name;
		readElement.size = nextElement.size;
	}

	/**
	 * 设置len和temp
	 * @param obj 
	 */
	private void setLenAndTemp(TempVariable obj, int i) {
		obj.len = i;
		obj.temp = new byte[obj.len];
	}

	/**
	 * 获取className的文件路径
	 * @param className
	 * @return
	 */
	private String getClassfilePath(String className) {

		String subpath = StringUtil.replaceClassToPath(className);

		String filepath = Constants.classpath + "/" + subpath + ".class";
		
		return filepath;
	}
	
	
}
