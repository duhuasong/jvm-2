package jvm.classloader.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.IClassLoader;
import jvm.classloader.classfile.ClassFile;
import jvm.classloader.classfile.ConstantFile;
import jvm.classloader.classstruct.ClassElement;
import jvm.classloader.classstruct.ClassReadCounter;
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
			ClassElement readElement = counter.getCurElement();
			
			int constant_pool_count = 0;
			int constant_pool_pointer = 1;
			//��ǰ��ȡ�ĳ�������
			String constant_type = null;
			//��ǰ��ȡ�ĳ������Ͷ�Ӧ��Ƭ�Σ�utf8��Ӧ3��class���Ͷ�Ӧ2�����ֵȣ�
			int constant_type_part = 1;
			//��ǰ��ȡ�ķ������Ͷ�Ӧ��Ƭ�Σ�5�����֣�access_flags��name_index��descriptor_index��attributes_count��attributes_info��
			int method_info_part = 1;
			
			
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
							//��������ر�����������ִ����һ��Ԫ��
							if(constant_pool_pointer >= constant_pool_count){
								readElement = counter.getCurElement();
								len = readElement.size;
								temp = new byte[len];
								continue;
							}else{
								//���ñ�������ȡ��һ������
								constant_type = null;
								constant_type_part = 1;
								len = 1;
								temp = new byte[len];
								continue;
							}
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
							
							//��������ر�����������ִ����һ��Ԫ��
							if(constant_pool_pointer >= constant_pool_count){
								readElement = counter.getCurElement();
								len = readElement.size;
								temp = new byte[len];
								continue;
							}else{
								//���ñ�������ȡ��һ������
								constant_type = null;
								constant_type_part = 1;
								len = 1;
								temp = new byte[len];
								continue;
							}
						}
					}else if(Constants.ConstantType.method.equals(constant_type) || Constants.ConstantType.field.equals(constant_type) || Constants.ConstantType.nameAndType.equals(constant_type)){//�������������0a
						//�����ǰ��ȡ���ǳ����ĵ�һ����
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
							
							//��������ر�����������ִ����һ��Ԫ��
							if(constant_pool_pointer >= constant_pool_count){
								readElement = counter.getCurElement();
								len = readElement.size;
								temp = new byte[len];
								continue;
							}else{
								//���ñ�������ȡ��һ������
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
					
					//����ӿ���Ϊ0����ô�ӿ�interfaces_arrayû�У���������
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
					
					//����ֶ���Ϊ0����ô�ӿ�interfaces_arrayû�У���������
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
					}
					
					
					
				}
				
				//--------ÿ����֧Ĭ������readElement��len��temp----------
				readElement = counter.getCurElement();
				len = readElement.size;
				temp = new byte[len];
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
