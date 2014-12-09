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
			//��ǰ��ȡ�ĳ�������
			String constant_type = null;
			//��ǰ��ȡ�ĳ������Ͷ�Ӧ��Ƭ�Σ�utf8��Ӧ3��class���Ͷ�Ӧ2�ȣ�
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
					
					if(Constants.ConstantType.ClassType.equals(constant_type)){//�������������07��class����
						//�����ǰ��ȡ����class�����ĵ�һ����
						if(constant_type_part == 1){
							len = 2;
							temp = new byte[len];
							constant_type_part++;
							continue;
						}else if(constant_type_part == 2){
							int utf8_index = ByteHexUtil.getInt(new byte[]{temp[0],temp[1]}, false, 2);
							ConstantFile cf = new ConstantFile(Constants.ConstantType.ClassType,utf8_index);
							classFile.constantFiles.put(constant_pool_pointer++, cf);
							//���ñ�������ȡ��һ������
							constant_type = null;
							constant_type_part = 1;
							len = 1;
							temp = new byte[len];
							continue;
						}
					}else if(Constants.ConstantType.utf8.equals(constant_type)){//�������������01
						//�����ǰ��ȡ���ǳ����ĵ�һ����
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
							
							//���ñ�������ȡ��һ������
							constant_type = null;
							constant_type_part = 1;
							len = 1;
							temp = new byte[len];
							continue;
						}
					}else if(Constants.ConstantType.method.equals(constant_type)){//�������������0a
						
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
