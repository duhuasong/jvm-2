package jvm.classloader.classfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFile {
	
	//TODO 暂时放16进制数
	public String magic;
	
	//TODO 暂时放16进制数
	public String minor_version;
	
	//TODO 暂时放16进制数
	public String major_version;
	
	public int constant_pool_count;
	
	public Map<Integer,ConstantFile> constantFiles = new HashMap<Integer, ConstantFile>();
	
	public String access_flags;
	
	public String this_class;
	
	public String super_class;
	
	public int interfaces_count;
	
	public String interfaces_array;
	
	public int fields_count;
	
	public String fields_array;
	
	public int methods_count;
	
	public List<FieldMethodFile> methods_array = new ArrayList<FieldMethodFile>();
	
	
	public String getMagic() {
		return magic;
	}

	public void setMagic(String magic) {
		this.magic = magic;
	}

	public String getMinor_version() {
		return minor_version;
	}

	public void setMinor_version(String minor_version) {
		this.minor_version = minor_version;
	}

	public String getMajor_version() {
		return major_version;
	}

	public void setMajor_version(String major_version) {
		this.major_version = major_version;
	}

	public int getConstant_pool_count() {
		return constant_pool_count;
	}

	public void setConstant_pool_count(int constant_pool_count) {
		this.constant_pool_count = constant_pool_count;
	}

	public Map<Integer, ConstantFile> getConstantFiles() {
		return constantFiles;
	}

	public void setConstantFiles(Map<Integer, ConstantFile> constantFiles) {
		this.constantFiles = constantFiles;
	}

	public String getAccess_flags() {
		return access_flags;
	}

	public void setAccess_flags(String access_flags) {
		this.access_flags = access_flags;
	}

	public String getThis_class() {
		return this_class;
	}

	public void setThis_class(String this_class) {
		this.this_class = this_class;
	}

	public String getSuper_class() {
		return super_class;
	}

	public void setSuper_class(String super_class) {
		this.super_class = super_class;
	}

	public int getInterfaces_count() {
		return interfaces_count;
	}

	public void setInterfaces_count(int interfaces_count) {
		this.interfaces_count = interfaces_count;
	}

	public String getUtf8ConstantContentByIndex(int attribute_name_index) {
		ConstantFile con = constantFiles.get(attribute_name_index);
		return con.content;
	}

	public boolean hasRemainMethods() {
		return hasRemainFieldAndMethod('M',methods_count);
	}
	public boolean hasRemainFields() {
		return hasRemainFieldAndMethod('F',fields_count);
	}
	public boolean hasRemainFieldAndMethod(char type, int total) {
		int count = 0;
		for(FieldMethodFile fm : methods_array){
			if(fm.type == type){
				count++;
			}
		}
		return count != total;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("magic : ").append(magic).append("\n");
		sb.append("minor_version : ").append(minor_version).append("\n");
		sb.append("major_version : ").append(major_version).append("\n");
		//sb.append("------------------------------------").append("\n");
		sb.append("constant_pool_count : ").append(constant_pool_count).append("\n");
		for(int i=1 ; i<constant_pool_count ; i++){
			ConstantFile cf = this.constantFiles.get(i);
			if(cf != null){
				sb.append(i+" : ").append(cf.content).append("\n");
			}
		}
		sb.append("\n");
		//sb.append("------------------------------------").append("\n");
		sb.append("access_flags : ").append(access_flags).append("\n");
		sb.append("this_class : ").append(this_class).append("\n");
		sb.append("super_class : ").append(super_class).append("\n");
		sb.append("methods_count : ").append(methods_count).append("\n").append("\n");;
		for(FieldMethodFile mf : methods_array){
			sb.append(mf.toString()).append("\n");;
		}
		return sb.toString();
	}

	public String constantToString() {
		StringBuilder sb = new StringBuilder();
		for(int i=1 ; i<constant_pool_count ; i++){
			ConstantFile cf = this.constantFiles.get(i);
			if(cf != null){
				sb.append("\n");
				sb.append(i+" : ");
				if(cf.uft8_index != 0){
					sb.append("#").append(cf.uft8_index);
				}
				if(cf.pre_uft8_index != 0){
					sb.append("#").append(cf.pre_uft8_index);
				}
				if(cf.last_uft8_index != 0){
					sb.append("#").append(cf.last_uft8_index);
				}
				if(cf.content != null){
					sb.append(" ").append(cf.content);
				}
			}
		}
		sb.append("\n");
		return sb.toString();
	}

}
