package x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl;

import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeMapItemMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by sky91 on 2/5/15.
 */
public class ClassMapConverter {
	public Map<Class, String> getClassMap(TypeMapItemMap param) {
		Map<String, String> map = param.getTypeMap();
		Map<Class, String> result = new HashMap<>(map.size());
		try {
			for(Entry<String, String> entry : map.entrySet()) {
				result.put(Class.forName(entry.getKey()), entry.getValue());
			}
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public Map<Enum, String> getEnumMap(TypeMapItemMap param) {
		Map<String, Map<String, String>> map = param.getItemMap();
		Map<Enum, String> result = new HashMap<>();
		try {
			for(Entry<String, Map<String, String>> classEntry : map.entrySet()) {
				for(Entry<String, String> enumInstanceEntry : classEntry.getValue().entrySet()) {
					result.put(Enum.valueOf((Class) Class.forName(classEntry.getKey()), enumInstanceEntry.getKey()), enumInstanceEntry.getValue());
				}
			}
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
