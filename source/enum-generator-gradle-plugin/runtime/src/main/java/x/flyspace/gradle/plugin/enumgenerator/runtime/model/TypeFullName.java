package x.flyspace.gradle.plugin.enumgenerator.runtime.model;

/**
 * Created by sky91 on 12/17/14.
 */
public class TypeFullName {
	private String typePackageName;
	private String typeShortName;

	public TypeFullName(String typePackageName, String typeShortName) {
		this.typePackageName = typePackageName;
		this.typeShortName = typeShortName;
	}

	public TypeFullName(String typeFullName) {
		int index0 = typeFullName.lastIndexOf('.');
		this.typePackageName = typeFullName.substring(0, index0 < 0 ? 0 : index0);
		this.typeShortName = typeFullName.substring(index0 + 1, typeFullName.length());
	}

	public TypeFullName(FileFullName fileFullName) {
		this.typePackageName = fileFullName.getPathName().replaceAll("/", ".").replaceAll("\\\\", ".");
		this.typeShortName = fileFullName.getTypeShortName();
	}

	public TypeFullName(Class c) {
		this(c.getName());
	}

	public String getTypeFullName() {
		return typePackageName + "." + typeShortName;
	}

	public String getTypePackageName() {
		return typePackageName;
	}

	public String getTypeShortName() {
		return typeShortName;
	}

	public TypeFullName replaceTypeShortName(String search, String replace) {
		if(search != null && replace != null) {
			typeShortName = typeShortName.replaceAll(search, replace);
		}
		return this;
	}

	public TypeFullName fixTypeShortName(String prefix, String suffix) {
		if(prefix != null) {
			typeShortName = prefix + typeShortName;
		}
		if(suffix != null) {
			typeShortName = typeShortName + suffix;
		}
		return this;
	}

	@Override
	public int hashCode() {
		return getTypeFullName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getTypeFullName().equals(obj);
	}

	@Override
	public String toString() {
		return getTypeFullName();
	}
}
