package jvm.util.common;

public class StringUtil {
	
	public static String replacePathToClass(String src) {
		return  src.replace("/", ".");
	}
	
	public static String replaceClassToPath(String src) {
		return  src.replace(".", "/");
	}
}
