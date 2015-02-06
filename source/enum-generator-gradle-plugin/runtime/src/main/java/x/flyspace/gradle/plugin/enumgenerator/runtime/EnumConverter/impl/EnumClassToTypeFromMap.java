package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl;

import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumClassToType;

import java.util.Map;

/**
 * Created by sky91 on 2/2/15.
 */
public class EnumClassToTypeFromMap implements EnumClassToType {
	private Map<Class, String> map;

	public EnumClassToTypeFromMap(Map<Class, String> map) {
		this.map = map;
	}

	@Override
	public String enumClassToType(Class enumClass) {
		return map.get(enumClass);
	}
}
