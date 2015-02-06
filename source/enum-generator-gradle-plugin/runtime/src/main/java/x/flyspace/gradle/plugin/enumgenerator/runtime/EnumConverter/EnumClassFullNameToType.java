package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter;

/**
 * Created by sky91 on 2/3/15.
 */
public interface EnumClassFullNameToType {
	String enumClassFullNameToType(String enumClassFullName);

	String enumClassFullNameToType(String enumClassPackage, String enumClassShortName);
}
