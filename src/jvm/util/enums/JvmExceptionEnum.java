package jvm.util.enums;

public enum JvmExceptionEnum {
	
	EmptyMethod(1, "�˷�����ûʵ�֣��ȴ�ʵ�֡�");
	
	private int code;
	private String desc;

	private JvmExceptionEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	// ��ͨ����  
    public static String getDesc(int code) {  
        for (JvmExceptionEnum c : JvmExceptionEnum.values()) {  
            if (c.getCode() == code) {  
                return c.desc;  
            }  
        }  
        return null;  
    }  
	
}
