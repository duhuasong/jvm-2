package jvm.util.enums;

public enum ConstantTypeEnum {
	
	classType("classType", "07"), 
	field("field", "09"), 
	method("method", "0a"), 
	stringType("stringType", "08"), 
	nameAndType("nameAndType", "0c"),
	longType("longType", "05"),
	utf8("utf8", "01");

	private String name;
	private String code;
	private ConstantTypeEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	// ∆’Õ®∑Ω∑®  
    public static String getName(String code) {  
        for (ConstantTypeEnum c : ConstantTypeEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;  
    }  
}
