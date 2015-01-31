package jvm.memory.classinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jvm.util.exception.JvmException;

public class ClassInfo {
	
	private ClassInfo superClassInfo;
	
	//带包名:xxx.xxx.ClassName
	private String name;
	
	private Map<Integer,String> constants = new HashMap<Integer, String>();
	
	private List<MethodInfo> methods;
	
	private List<FieldInfo> fields;

	public ClassInfo(String name, List<MethodInfo> methods) {
		super();
		this.setName(name);
		this.methods = methods;
	}

	public ClassInfo(String name) {
		super();
		this.setName(name);
	}
	
	public ClassInfo() {
	}

	public List<MethodInfo> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}

	public MethodInfo getMethodByName(String name) {
		for(MethodInfo method : methods){
			if(method.getName().equals(name)){
				return method;
			}
		}
		return null;
	}
	
	public MethodInfo getMethod(String methodName, String methodType) {
		ClassInfo curClassInfo = this;
		do{
			for(MethodInfo method : curClassInfo.methods){
				if(method.getName().equals(methodName) && method.getDescriptor().equals(methodType)){
					return method;
				}
			}
		}while( (curClassInfo = curClassInfo.getSuperClassInfo()) != null );
		
		try {
			throw new JvmException("在classInfo["+name+"]中没有找到方法名为["+methodName+"]，方法类型为["+methodType+"]的方法");
		} catch (JvmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("className : ").append(name).append("\n").append("\n");
		for(MethodInfo mi : methods){
			sb.append(mi.toString()).append("\n");;
		}
		for(FieldInfo fi : fields){
			sb.append(fi.toString()).append("\n");;
		}
		return sb.toString();
	}

	public List<FieldInfo> getFields() {
		return fields;
	}

	public void setFields(List<FieldInfo> fields) {
		this.fields = fields;
	}

	public Map<Integer,String> getConstants() {
		return constants;
	}

	public void setConstants(Map<Integer,String> constants) {
		this.constants = constants;
	}

	public String getConstantByIndex(int i) throws JvmException {
		String content = constants.get(i);
		if(content == null){
			throw new JvmException("当前类["+name+"]索引为["+i+"]的常量没有从classFile中copy过来，该常量可能是新的常量类型，检查translateConstantFile方法。");
		}
		return constants.get(i);
	}

	public ClassInfo getSuperClassInfo() {
		return superClassInfo;
	}

	public void setSuperClassInfo(ClassInfo superClassInfo) {
		this.superClassInfo = superClassInfo;
	}


}
