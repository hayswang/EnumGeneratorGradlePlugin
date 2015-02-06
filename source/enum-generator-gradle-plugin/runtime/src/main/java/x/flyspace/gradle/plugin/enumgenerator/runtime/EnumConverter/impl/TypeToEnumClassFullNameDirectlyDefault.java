package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl;

import x.flyspace.gradle.plugin.enumgenerator.runtime.converter.PackageNameAndTypeConverter;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.TypeToEnumClassFullName;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeFullName;

/**
 * Created by sky91 on 2/2/15.
 */
public class TypeToEnumClassFullNameDirectlyDefault implements TypeToEnumClassFullName {
	private final PackageNameAndTypeConverter packageNameAndTypeConverter;

	public TypeToEnumClassFullNameDirectlyDefault(PackageNameAndTypeConverter packageNameAndTypeConverter) {
		this.packageNameAndTypeConverter = packageNameAndTypeConverter;
	}

	@Override
	public String typeToEnumClassFullName(String type) {
		String fullTypeName = packageNameAndTypeConverter.typeToPackageName(type);
		while(fullTypeName.endsWith(".")) {
			fullTypeName = fullTypeName.substring(0, fullTypeName.length() - 1);
		}
		int lastIndex = fullTypeName.lastIndexOf(".");
		String packageName = fullTypeName.substring(0, lastIndex);
		String typeShortName = fullTypeName.substring(lastIndex + 1, lastIndex + 2).toUpperCase() + fullTypeName.substring(lastIndex + 2);
		return new TypeFullName(packageName, typeShortName).getTypeFullName();
	}
}
