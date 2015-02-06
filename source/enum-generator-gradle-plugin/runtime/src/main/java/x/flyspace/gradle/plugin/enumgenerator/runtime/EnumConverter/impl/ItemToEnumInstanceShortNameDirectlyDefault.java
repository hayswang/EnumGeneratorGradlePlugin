package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl;

import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.ItemToEnumInstanceShortName;

/**
 * Created by sky91 on 2/2/15.
		*/
public class ItemToEnumInstanceShortNameDirectlyDefault implements ItemToEnumInstanceShortName {
	@Override
	public String itemToEnumInstanceShortName(String item) {
		return item;
	}
}
