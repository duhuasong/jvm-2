package jvm.util;

import jvm.util.common.StringUtil;


public class FieldUtil {
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * @param method_descripter
	 * @return
	 */
	public static String parseClassName(String method_descripter) {
		String[] arr = method_descripter.split("\\.");
		return StringUtil.replacePathToPoint(arr[0]);
	}
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * @param method_descripter
	 * @return
	 */
	public static String parseFieldTypeWithPoint(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return StringUtil.replacePathToPoint(arr[1]).replace(";", "");
	}
	public static String parseFieldTypeWithPath(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return arr[1].replace(";", "");
	}
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * 带类名的字段名
	 * @param method_descripter
	 * @return
	 */
	public static String parseFieldFullName(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return StringUtil.replacePathToPoint(arr[0]);
	}
	/**
	 * java/lang/System.out:Ljava/io/PrintStream;
	 * 字段名
	 * @param method_descripter
	 * @return
	 */
	public static String parseFieldName(String method_descripter) {
		String[] arr = method_descripter.split(":");
		return arr[0].split("\\.")[1];
	}

	

}
