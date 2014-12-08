package jvm.classloader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.IClassLoader;
import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ClassFileCounter;
import jvm.classloader.classfile.ElementDescripter;
import jvm.util.ByteHexUtil;

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
			ClassFileCounter counter = new ClassFileCounter();
			//下一元素
			ElementDescripter readElement = counter.getCurElement();
			//当前元素已经读完
			boolean isElementReadEnd = true;
			
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
					int constant_pool_count = ByteHexUtil.getInt(temp, false, temp.length);
					classFile.setConstant_pool_count(constant_pool_count);
					
					readElement = counter.getCurElement();
					len = readElement.size;
					temp = new byte[len];
				}
				
				/*String hex = ByteHexUtil.bytesToHexString(temp);
				System.out.println(hex);*/
				
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
