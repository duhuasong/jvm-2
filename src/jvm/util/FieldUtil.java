package jvm.util;


public class FieldUtil {
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * @param method_descripter
	 * @return
	 */
	public static String parseClassName(String method_descripter) {
		String[] arr = method_descripter.split("\\.");
		return StringUtil.replacePathToClass(arr[0]);
	}
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * @param method_descripter
	 * @return
	 */
	public static String parseFieldType(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return StringUtil.replacePathToClass(arr[1]).replace(";", "");
	}
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * @param method_descripter
	 * @return
	 */
	public static String parseFieldFullName(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return StringUtil.replacePathToClass(arr[0]);
	}

	

}
