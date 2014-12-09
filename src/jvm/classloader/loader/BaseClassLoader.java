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
			//当前读取的常量类型
			String constant_type = null;
			//当前读取的常量类型对应的片段（utf8对应3，class类型对应2等）
			int constant_type_part = 1;
			
			
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
							//重置变量，读取下一个常量
							constant_type = null;
							constant_type_part = 1;
							len = 1;
							temp = new byte[len];
							continue;
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
							
							//重置变量，读取下一个常量
							constant_type = null;
							constant_type_part = 1;
							len = 1;
							temp = new byte[len];
							continue;
						}
					}else if(Constants.ConstantType.method.equals(constant_type)){//如果常量类型是0a
						
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
