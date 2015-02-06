package x.flyspace.gradle.plugin.enumgenerator.runtime.model;

/**
 * Created by sky91 on 12/17/14.
 */
public class FileFullName {
	private String pathName;
	private String typeShortName;
	private String extensionName;

	public FileFullName(String pathName, String typeShortName, String extensionName) {
		this.pathName = pathName;
		this.typeShortName = typeShortName;
		this.extensionName = extensionName;
	}

	public FileFullName(String fileFullName) {
		int index0 = fileFullName.lastIndexOf('/');
		int index1 = fileFullName.lastIndexOf('.');
		this.pathName = fileFullName.substring(0, index0 < 0 ? 0 : index0);
		this.typeShortName = fileFullName.substring(index0 + 1, index1 < (index0 + 1) ? (index0 + 1) : index1);
		this.extensionName = fileFullName.substring(index1 + 1, fileFullName.length());
	}

	public FileFullName(TypeFullName typeFullName, String extensionName) {
		this.pathName = typeFullName.getTypePackageName().replaceAll("\\.", "/");
		this.typeShortName = typeFullName.getTypeShortName();
		this.extensionName = extensionName;
	}

	public FileFullName(String pathName, String fileShortName) {
		int index0 = fileShortName.lastIndexOf('.');
		this.pathName = pathName;
		this.typeShortName = fileShortName.substring(0, index0 < 0 ? 0 : index0);
		this.extensionName = fileShortName.substring(index0 + 1, fileShortName.length());
	}

	public String getPathName() {
		return pathName;
	}

	public String getTypeShortName() {
		return typeShortName;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public String getFileShortName() {
		return typeShortName + "." + extensionName;
	}

	public String getFileFullName() {
		return pathName + "/" + getFileShortName();
	}

	public FileFullName replaceTypeShortName(String search, String replace) {
		if(search != null && replace != null) {
			typeShortName = typeShortName.replaceAll(search, replace);
		}
		return this;
	}

	public FileFullName fixTypeShortName(String prefix, String suffix) {
		if(prefix != null) {
			typeShortName = prefix + typeShortName;
		}
		if(suffix != null) {
			typeShortName = typeShortName + suffix;
		}
		return this;
	}
}
