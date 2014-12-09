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
		//��ǰ���̵�class��·�����磺D:\workspaces\myeclipse\wtms3\ztest\bin
		String classpath = System.getProperty("java.class.path");

		String subpath = className.replace(".", "/");

		String filepath = classpath + "/" + subpath + ".class";

		FileInputStream fis = null;
		
		ClassFile classFile = new ClassFile();

		try {
			fis = new FileInputStream(new File(filepath));
			//�ӳ����ؿ�ʼ��
			ClassReadCounter counter = new ClassReadCounter();
			//��һԪ��
			ElementDescripter readElement = counter.getCurElement();
			
			int constant_pool_count = 0;
			int constant_pool_pointer = 1;
			//��ǰ���ڶ�ȡ����utf8�������͵����ݣ��ַ�����
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
					//�����ǰ���ڶ�ȡ����tf8�������͵����ݣ��ַ���������û��type������һ˵��
					if(isUtf8_content){
						String content = ByteHexUtil.getStringFromUtf8(temp);
						ConstantFile cf = new ConstantFile(Constants.ConstantType.utf8,content);
						classFile.constantFiles.put(constant_pool_pointer++, cf);
						isUtf8_content = false;
						
						len = 3;
						temp = new byte[len];
						continue;
					}
					//�ҵ��������ͣ���һ���ֽڣ���utf8�����������ֽڣ�
					String type = ByteHexUtil.bytesToHexString(new byte[]{temp[0]});
					int last_u2 = ByteHexUtil.getInt(new byte[]{temp[1],temp[2]}, false, 2);
					
					if(Constants.ConstantType.ClassType.equals(type)){//�������������07��ConstantFile�������ͺ�utf8����
						ConstantFile cf = new ConstantFile(type,last_u2);
						classFile.constantFiles.put(constant_pool_pointer++, cf);
						
						len = 3;
						temp = new byte[len];
					}else if(Constants.ConstantType.utf8.equals(type)){//�������������01���������ֶ����ַ����ĳ���
						len = last_u2;
						temp = new byte[len];
						isUtf8_content = true;
					}else if(Constants.ConstantType.method.equals(type)){//�������������0a�������ĸ��ֶΣ�ǰ������class����������������NameAndType����
						len = last_u2;
						temp = new byte[len];
						isUtf8_content = true;
					}
					
					//��������ر�����������ִ����һ��Ԫ��
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
