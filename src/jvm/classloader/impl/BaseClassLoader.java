package jvm.classloader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jvm.classloader.IClassLoader;
import jvm.util.ByteHexUtil;

public class BaseClassLoader implements IClassLoader {

	@Override
	public void loadClass(String className) {
		//当前工程的class根路径，如：D:\workspaces\myeclipse\wtms3\ztest\bin
		String classpath = System.getProperty("java.class.path");

		String subpath = className.replace(".", "/");

		String filepath = classpath + "/" + subpath + ".class";

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(new File(filepath));
			int len = 8;
			byte[] temp = new byte[len];

			while ((fis.read(temp, 0, len)) != -1) {
				String hex = ByteHexUtil.bytesToHexString(temp);
				System.out.println(hex);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
