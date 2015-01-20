package jvm.classloader.tool;

import java.util.HashMap;
import java.util.Map;
/**
 * 16进制数字和字节码描述之间的映射
 * @author yangrui
 *
 */
public class ByteCodeMap {
	
	public final static Map<String,ByteCodeDesc> map = new HashMap<String,ByteCodeDesc>();
	
	static{
		
		putByteCodeDesc(new ByteCodeDesc("03", "iconst_0"));
		putByteCodeDesc(new ByteCodeDesc("04", "iconst_1"));
		putByteCodeDesc(new ByteCodeDesc("05", "iconst_2"));
		putByteCodeDesc(new ByteCodeDesc("06", "iconst_3"));
		putByteCodeDesc(new ByteCodeDesc("07", "iconst_4"));
		putByteCodeDesc(new ByteCodeDesc("08", "iconst_5"));
		
		putByteCodeDesc(new ByteCodeDesc("14", "ldc2_w",2));
		putByteCodeDesc(new ByteCodeDesc("1a", "iload_0"));
		putByteCodeDesc(new ByteCodeDesc("1b", "iload_1"));
		putByteCodeDesc(new ByteCodeDesc("1c", "iload_2"));
		putByteCodeDesc(new ByteCodeDesc("1d", "iload_3"));
		putByteCodeDesc(new ByteCodeDesc("1f", "lload_1"));
		putByteCodeDesc(new ByteCodeDesc("2a", "aload_0"));
		putByteCodeDesc(new ByteCodeDesc("2b", "aload_1"));
		
		putByteCodeDesc(new ByteCodeDesc("3c", "istore_1"));
		putByteCodeDesc(new ByteCodeDesc("3d", "istore_2"));
		putByteCodeDesc(new ByteCodeDesc("3e", "istore_3"));
		putByteCodeDesc(new ByteCodeDesc("4c", "astore_1"));
		
		putByteCodeDesc(new ByteCodeDesc("60", "iadd"));
		putByteCodeDesc(new ByteCodeDesc("b1", "return"));
		putByteCodeDesc(new ByteCodeDesc("ac", "ireturn"));
		putByteCodeDesc(new ByteCodeDesc("ad", "lreturn"));
		putByteCodeDesc(new ByteCodeDesc("59", "dup"));
		
		putByteCodeDesc(new ByteCodeDesc("10", "bipush",1));
		putByteCodeDesc(new ByteCodeDesc("12", "ldc",1));
		
		putByteCodeDesc(new ByteCodeDesc("b0", "areturn"));
		putByteCodeDesc(new ByteCodeDesc("b2", "getstatic",2));
		putByteCodeDesc(new ByteCodeDesc("b4", "getfield",2));
		putByteCodeDesc(new ByteCodeDesc("b5", "putfield",2));
		putByteCodeDesc(new ByteCodeDesc("b6", "invokevirtual",2));
		putByteCodeDesc(new ByteCodeDesc("b7", "invokespecial",2));
		putByteCodeDesc(new ByteCodeDesc("b8", "invokestatic",2));
		putByteCodeDesc(new ByteCodeDesc("bb", "new",2));
		
		
		
	}

	public static ByteCodeDesc get(String key) {
		return map.get(key);
	}
	
	
	
	private static void putByteCodeDesc(ByteCodeDesc byteCodeDesc) {
		map.put(byteCodeDesc.hex,  byteCodeDesc);
	}


	/**
	 * 内部类
	 * @author yangrui
	 *
	 */
	public static class ByteCodeDesc {
		
		public String hex;
		
		public String desc;
		//操作码后面操作数的字节数
		public int index_number;
		
		public ByteCodeDesc(String hex, String desc, int index_number) {
			super();
			this.hex = hex;
			this.desc = desc;
			this.index_number = index_number;
		}

		public ByteCodeDesc(String hex, String desc) {
			super();
			this.hex = hex;
			this.desc = desc;
		}
	}

}
