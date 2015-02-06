package jvm.util;

import jvm.util.common.ByteHexUtil;

public class AccessFlagUtil {
	
	private final static String ACC_SYNCHRONIZED = "0020";
	
	//private final static String ACC_PUBLIC = "0020";
	
	//private final static String ACC_PRIVATE = "0020";
	
	//private final static String ACC_PROTECTED = "0020";
	
	private final static String ACC_STATIC = "0020";

	public static boolean isSynchronized(String access_flags) {
		return handle(access_flags,ACC_SYNCHRONIZED);
	}
	
	public static boolean isStatic(String access_flags) {
		return handle(access_flags,ACC_STATIC);
	}

	private static boolean handle(String access_flags, String specified_access_flag) {
		int access_custom_int = ByteHexUtil.fromHexToInt(specified_access_flag);
		int access_flags_int = ByteHexUtil.fromHexToInt(access_flags);
		int result = access_flags_int & access_custom_int;
		return result == access_custom_int;
	}

}
