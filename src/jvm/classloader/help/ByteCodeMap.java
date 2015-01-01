package jvm.classloader.help;

import java.util.HashMap;
import java.util.Map;

public class ByteCodeMap {
	
	public final static Map<String,ByteCodeDesc> map = new HashMap<String,ByteCodeDesc>();
	
	static{
		map.put("03",  new ByteCodeDesc("03", "iconst_0"));
		map.put("04",  new ByteCodeDesc("04", "iconst_1"));
		map.put("05",  new ByteCodeDesc("05", "iconst_2"));
		map.put("06",  new ByteCodeDesc("06", "iconst_3"));
		map.put("07",  new ByteCodeDesc("07", "iconst_4"));
		map.put("08",  new ByteCodeDesc("08", "iconst_5"));
		
		map.put("1a",  new ByteCodeDesc("1a", "iload_0"));
		map.put("1b",  new ByteCodeDesc("1b", "iload_1"));
		map.put("1c",  new ByteCodeDesc("1c", "iload_2"));
		map.put("1d",  new ByteCodeDesc("1d", "iload_3"));
		map.put("3c",  new ByteCodeDesc("3c", "istore_1"));
		map.put("3d",  new ByteCodeDesc("3d", "istore_2"));
		map.put("3e",  new ByteCodeDesc("3e", "istore_3"));
		map.put("10",  new ByteCodeDesc("10", "bipush",1));
		map.put("b8",  new ByteCodeDesc("b8", "invokestatic",2));
		map.put("b2",  new ByteCodeDesc("b2", "getstatic",2));
		map.put("b6",  new ByteCodeDesc("b6", "invokevirtual",2));
		map.put("60",  new ByteCodeDesc("60", "iadd"));
		map.put("b1",  new ByteCodeDesc("b1", "return"));
		map.put("ac",  new ByteCodeDesc("ac", "ireturn"));
		map.put("bb",  new ByteCodeDesc("bb", "new",2));
	}

	public static ByteCodeDesc get(String key) {
		return map.get(key);
	}
	

}
