package jvm.classloader.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.IClassLoader;
import jvm.classloader.assist.ClassReadCounter;
import jvm.classloader.assist.ElementDescripter;
import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.util.ByteHexUtil;
import jvm.util.Constants;

public class BaseClassLoader implements IClassLoader {

	@Override
	public void loadClass(String className) {
		//当前工程的class根路径，如：D:\workspaces\myeclipse\wtms3\ztest\bin
		String classpath = System.getProperty("java.class.path");

		String subpath = className.replace(".", "/");

		String filepath = classpath + "/" + subpath + ".class";

		FileInputStream fis = null;
		
		ClassFile classFile = new ClassFile();

		try {
			fis = new FileInputStream(new File(filepath));
			//从常量池开始读
			ClassReadCounter counter = new ClassReadCounter();
			//下一元素
			ElementDescripter readElement = counter.getCurElement();
			
			int constant_pool_count = 0;
			int constant_pool_pointer = 1;
			//当前正在读取的是utf8常量类型的内容（字符串）
			boolean isUtf8_content = false;
			
			int len = readElement.size;
			byte[] temp = new byte[len];
			
			while ((fis.read(temp, 0, len)) != -1) {
				
				if(readElement.name.equals("magic")){
					String hex = ByteHexUtil.bytesToHexString(temp);
					classFile.setMagic(hex);
					
					readElement = counter.getCurElement();
					len = readElement.size;
					temp = new byte[len];
				}else if(readElement.name.equals("minor_version")){
					String hex = ByteHexUtil.bytesToHexString(temp);
					classFile.setMinor_version(hex);
					
					readElement = counter.getCurElement();
					len = readElement.size;
					temp = new byte[len];
				}else if(readElement.name.equals("major_version")){
					String hex = ByteHexUtil.bytesToHexString(temp);
					classFile.setMajor_version(hex);
					
					readElement = counter.getCurElement();
					len = readElement.size;
					temp = new byte[len];
				}else if(readElement.name.equals("constant_pool_count")){
					constant_pool_count = ByteHexUtil.getInt(temp, false, temp.length);
					classFile.setConstant_pool_count(constant_pool_count);
					
					readElement = counter.getCurElement();
					len = readElement.size;
					temp = new byte[len];
				}else if(readElement.name.equals("constant_pool_array")){
					//如果当前正在读取的是tf8常量类型的内容（字符串），就没有type，索引一说了
					if(isUtf8_content){
						String content = ByteHexUtil.getStringFromUtf8(temp);
						ConstantFile cf = new ConstantFile(Constants.ConstantType.utf8,content);
						classFile.constantFiles.put(constant_pool_pointer++, cf);
						isUtf8_content = false;
						
						len = 3;
						temp = new byte[len];
						continue;
					}
					//找到常量类型（第一个字节），utf8索引（后两字节）
					String type = ByteHexUtil.bytesToHexString(new byte[]{temp[0]});
					int last_u2 = ByteHexUtil.getInt(new byte[]{temp[1],temp[2]}, false, 2);
					
					if(Constants.ConstantType.ClassType.equals(type)){//如果常量类型是07，ConstantFile保存类型和utf8索引
						ConstantFile cf = new ConstantFile(type,last_u2);
						classFile.constantFiles.put(constant_pool_pointer++, cf);
						
						len = 3;
						temp = new byte[len];
					}else if(Constants.ConstantType.utf8.equals(type)){//如果常量类型是01，后两个字段是字符串的长度
						len = last_u2;
						temp = new byte[len];
						isUtf8_content = true;
					}else if(Constants.ConstantType.method.equals(type)){//如果常量类型是0a，后面四个字段，前两个是class的索引，后两个是NameAndType索引
						len = last_u2;
						temp = new byte[len];
						isUtf8_content = true;
					}
					
					//如果常量池遍历结束，则执行下一个元素
					if(constant_pool_pointer >= constant_pool_count){
						readElement = counter.getCurElement();
						len = readElement.size;
						temp = new byte[len];
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
