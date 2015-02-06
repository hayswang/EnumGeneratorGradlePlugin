package x.flyspace.gradle.plugin.enumgenerator.core;

import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeFullName;

/**
 * Created by sky91 on 2/5/15.
 */
public class EnumUtilInfo {
	private TypeFullName typeFullName;
	private String getEnumMethodName;
	private String getMethodName;

	public EnumUtilInfo() {
	}

	public EnumUtilInfo(TypeFullName typeFullName, String getEnumMethodName, String getMethodName) {
		this.typeFullName = typeFullName;
		this.getEnumMethodName = getEnumMethodName;
		this.getMethodName = getMethodName;
	}

	public TypeFullName getTypeFullName() {
		return typeFullName;
	}

	public void setTypeFullName(TypeFullName typeFullName) {
		this.typeFullName = typeFullName;
	}

	public String getGetEnumMethodName() {
		return getEnumMethodName;
	}

	public void setGetEnumMethodName(String getEnumMethodName) {
		this.getEnumMethodName = getEnumMethodName;
	}

	public String getGetMethodName() {
		return getMethodName;
	}

	public void setGetMethodName(String getMethodName) {
		this.getMethodName = getMethodName;
	}
}
