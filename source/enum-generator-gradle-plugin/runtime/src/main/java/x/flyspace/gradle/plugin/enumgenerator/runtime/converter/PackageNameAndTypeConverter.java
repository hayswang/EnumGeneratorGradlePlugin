package x.flyspace.gradle.plugin.enumgenerator.runtime.converter;

/**
 * Created by sky91 on 2/3/15.
 */
public interface PackageNameAndTypeConverter {
	String packageNameToType(String packageName);

	String typeToPackageName(String type);
}
