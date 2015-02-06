package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl;

import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumInstanceToItem;

import java.util.Map;

/**
 * Created by sky91 on 2/3/15.
 */
public class EnumInstanceToItemFromMap implements EnumInstanceToItem {
	private Map<Enum, String> map;

	public EnumInstanceToItemFromMap(Map<Enum, String> map) {
		this.map = map;
	}

	@Override
	public String enumInstanceToItem(Enum e) {
		return map.get(e);
	}
}
