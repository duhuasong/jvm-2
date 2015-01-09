package jvm.util.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jvm.util.Constants;

public class FileUtil {

	public static ArrayList<File> getFileList(Object obj) {
		File directory = null;
		if (obj instanceof File) {
			directory = (File) obj;
		} else {
			directory = new File(obj.toString());
		}
		ArrayList<File> files = new ArrayList<File>();
		if (directory.isFile()) {
			files.add(directory);
			return files;
		} else if (directory.isDirectory()) {
			File[] fileArr = directory.listFiles();
			for (int i = 0; i < fileArr.length; i++) {
				File fileOne = fileArr[i];
				files.addAll(getFileList(fileOne));
			}
		}
		return files;
	}

	public static List<String> getClassNames(String packagepath) {
		List<String> names = new ArrayList<>();
		File dir = new File(packagepath);
		ArrayList<File> files = getFileList(dir);
		String className = null;
		for(File file : files){
			className = file.getName().split("\\.")[0];
			names.add(className);
		}
		return names;
	}
	
	public static void main(String[] args) {
		String processorPackage = Constants.classpath+"/jvm/engine/processor";
		System.out.println(getClassNames(processorPackage).get(0));
		
	}

}
