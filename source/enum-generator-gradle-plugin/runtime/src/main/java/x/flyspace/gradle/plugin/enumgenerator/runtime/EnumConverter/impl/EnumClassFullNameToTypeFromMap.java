package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl;

import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumClassFullNameToType;

import java.util.Map;

/**
 * Created by sky91 on 2/3/15.
 */
public class EnumClassFullNameToTypeFromMap implements EnumClassFullNameToType {
	private Map<String, String> map;

	public EnumClassFullNameToTypeFromMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String enumClassFullNameToType(String enumClassFullName) {
		return map.get(enumClassFullName);
	}

	@Override
	public String enumClassFullNameToType(String enumClassPackage, String enumClassShortName) {
		return map.get(enumClassPackage + "." + enumClassShortName);
	}
}
