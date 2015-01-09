package jvm.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	//当前工程的class根路径，如：D:\workspaces\myeclipse\wtms3\ztest\bin
	public final static String classpath = System.getProperty("java.class.path");
	
	public final static Map<String,String> typeClassMap = new HashMap<String,String>();
	
	static{
		typeClassMap.put(VarType.Integer_Type, "java.lang.Integer");
	}
	
	public static class Scope {

		public final static String PUBLIC = "public";

	}
	
	
	public static class VarType {

		public final static String Object_Type = "L";
		
		public final static String Integer_Type= "I";
		
		public final static String Byte_Type= "B";
		
		public final static String Char_Type= "C";
		
		public final static String Double_Type= "D";
		
		public final static String Float_Type= "F";
		
		public final static String Long_Type= "J";
		
		public final static String Short_Type= "S";
		
		public final static String Boolean_Type= "Z";
		
		public final static String Void_Type= "V";

	}
	
	
	public static class ConstantType {

		public final static String classType = "07";
		
		public final static String utf8 = "01";
		
		public final static String field = "09";
		
		public final static String method = "0a";
		
		public final static String nameAndType = "0c";
		
		public final static String stringType = "08";

	}
	
	public static class ConstantLinkSymbol {

		public final static String nameAndType = ":";
		
		public final static String methodAndField = ".";
		
	}
	
	public static class AttributeType {

		public final static String code = "Code";
		
		public final static String constantValueTyep = "constantValueTyep";
		
	}
	
	
}

