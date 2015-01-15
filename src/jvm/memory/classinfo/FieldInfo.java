package jvm.memory.classinfo;

public class FieldInfo {
	
	private String name;
	//¼ò½àµÄdescriptor£¬Èç£ºI
	private String descriptor;
	
	private Object defaultValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public FieldInfo() {
		super();
	}

	public FieldInfo(String name, String descriptor) {
		super();
		this.name = name;
		this.descriptor = descriptor;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("fieldName : ").append(name).append("\n");
		sb.append("descriptor : ").append(descriptor).append("\n");
		sb.append("defaultValue : ").append(defaultValue).append("\n");
		return sb.toString();
	}
	
}
