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
     * ��8�ֽڵ�byte����ת��һ��longֵ 
     * @param byteArray 
     * @return ת�����long����ֵ 
     */  
    public static long byteArrayToInt(byte[] byteArray) {  
        byte[] a = new byte[8];  
        int i = a.length - 1, j = byteArray.length - 1;  
        for (; i >= 0; i--, j--) {// ��b��β��(��intֵ�ĵ�λ)��ʼcopy����  
            if (j >= 0)  
                a[i] = byteArray[j];  
            else  
                a[i] = 0;// ���b.length����4,�򽫸�λ��0  
        }  
        // ע��˴���byte����ת����int���������ڣ������ת����Ҫ���Ƚ������е�Ԫ��ת����long��������λ������  
        // ��ֱ����λ�Ʋ������ò�����ȷ�������ΪJavaĬ�ϲ�������ʱ�������������Ὣ������Ϊint�����Դ����˴�����ע�⡣  
        long v0 = (long) (a[0] & 0xff) << 56;// &0xff��byteֵ�޲���ת��int,����Java�Զ�����������,�ᱣ����λ�ķ���λ  
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
