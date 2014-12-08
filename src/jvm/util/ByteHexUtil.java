package jvm.util;

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

	/* byte to int */
	public final static int getInt(byte[] buf, boolean asc, int len) {
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

	public static String getStringFromUtf8(byte[] temp) {
		try {
			return new String(temp, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
		return null;
	}

}
