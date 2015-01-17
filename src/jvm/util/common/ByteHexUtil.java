package jvm.util.common;

import java.io.UnsupportedEncodingException;

public class ByteHexUtil {

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static int getInt(byte[] buf, boolean asc, int len) {
		if (buf == null) {
			throw new IllegalArgumentException("byte array is null!");
		}
		if (len > 4) {
			throw new IllegalArgumentException("byte array size > 4 !");
		}
		int r = 0;
		if (asc)
			for (int i = len - 1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		else
			for (int i = 0; i < len; i++) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		return r;
	}
	
	 /** 
     * 将8字节的byte数组转成一个long值 
     * @param byteArray 
     * @return 转换后的long型数值 
     */  
    public static long byteArrayToInt(byte[] byteArray) {  
        byte[] a = new byte[8];  
        int i = a.length - 1, j = byteArray.length - 1;  
        for (; i >= 0; i--, j--) {// 从b的尾部(即int值的低位)开始copy数据  
            if (j >= 0)  
                a[i] = byteArray[j];  
            else  
                a[i] = 0;// 如果b.length不足4,则将高位补0  
        }  
        // 注意此处和byte数组转换成int的区别在于，下面的转换中要将先将数组中的元素转换成long型再做移位操作，  
        // 若直接做位移操作将得不到正确结果，因为Java默认操作数字时，若不加声明会将数字作为int型来对待，此处必须注意。  
        long v0 = (long) (a[0] & 0xff) << 56;// &0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位  
        long v1 = (long) (a[1] & 0xff) << 48;  
        long v2 = (long) (a[2] & 0xff) << 40;  
        long v3 = (long) (a[3] & 0xff) << 32;  
        long v4 = (long) (a[4] & 0xff) << 24;  
        long v5 = (long) (a[5] & 0xff) << 16;  
        long v6 = (long) (a[6] & 0xff) << 8;  
        long v7 = (long) (a[7] & 0xff);  
        return v0 + v1 + v2 + v3 + v4 + v5 + v6 + v7;  
    }  

	public static String getStringFromUtf8(byte[] temp) {
		try {
			return new String(temp, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
		return null;
	}

	public static int fromHexToInt(String hex) {
		return Integer.parseInt(hex,16);
	}

}
