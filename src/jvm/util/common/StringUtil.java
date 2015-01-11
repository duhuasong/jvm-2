package jvm.util.common;

public class StringUtil {
	
	public static String replacePathToPoint(String src) {
		return  src.replace("/", ".");
	}
	
	public static String replaceClassToPath(String src) {
		return  src.replace(".", "/");
	}
}
