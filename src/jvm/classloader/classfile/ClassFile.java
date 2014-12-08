package jvm.classloader.classfile;

import java.util.HashMap;
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
	
	public String interfaces_count;

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

	public String getInterfaces_count() {
		return interfaces_count;
	}

	public void setInterfaces_count(String interfaces_count) {
		this.interfaces_count = interfaces_count;
	}
	
	
	

}
