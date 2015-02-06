package x.a.b;

import com.fasterxml.jackson.databind.ObjectMapper;
import x.flyspace.gradle.plugin.enumgenerator.runtime.converter.StreamToObject;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumClassToType;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumInstanceToItem;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.ItemToEnumInstanceShortName;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl.ClassMapConverter;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl.EnumClassToTypeFromMap;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl.EnumInstanceToItemFromMap;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl.ItemToEnumInstanceShortNameDirectlyDefault;
import x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap.EnumMemoryMapResolver;
import x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap.JsonMapKeyAdapter;
import x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap.StandardMemoryMapResolver;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.MemoryIndex;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeMapItemMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky91 on 2/5/15.
 */
public class AAA {
	private static final EnumMemoryMapResolver resolver;
	private static final ItemToEnumInstanceShortName itemToEnumInstanceShortName;

	static {
		StreamToObject objectConverter = new StreamToObject();
		ClassMapConverter classMapConverter = new ClassMapConverter();
		MemoryIndex index = (MemoryIndex) objectConverter.convert(AAA.class.getClassLoader().getResourceAsStream("index"));
		TypeMapItemMap typeMap = (TypeMapItemMap) objectConverter.convert(AAA.class.getClassLoader().getResourceAsStream("type"));
		EnumInstanceToItem enumInstanceToItem = new EnumInstanceToItemFromMap(classMapConverter.getEnumMap(typeMap));
		EnumClassToType enumClassToType = new EnumClassToTypeFromMap(classMapConverter.getClassMap(typeMap));
		StandardMemoryMapResolver standardMemoryMapResolver = new StandardMemoryMapResolver(index,
																							new JsonMapKeyAdapter(new ObjectMapper().writer()));
		resolver = new EnumMemoryMapResolver(standardMemoryMapResolver, enumClassToType, enumInstanceToItem);
		itemToEnumInstanceShortName = new ItemToEnumInstanceShortNameDirectlyDefault();
	}

	public static Object getProperty(Enum e, String key) {
		Map<String, Object> map = new HashMap<>();
		map.put(EnumMemoryMapResolver.CLASS, e.getClass());
		map.put(EnumMemoryMapResolver.INSTANCE, e);
		Collection<Map<String, Object>> result = resolver.get(map);
		if(result == null || result.size() == 0) {
			return null;
		}
		if(result.size() > 1) {
			throw new RuntimeException("Enum data exception. More than 1 result.");
		}
		return result.toArray(new Map[1])[0].get(key);
	}

	public static <T extends Enum<T>> T getEnum(Class<T> c, String[] keys, Object[] values) {
		Map<String, Object> map = new HashMap<>();
		map.put(EnumMemoryMapResolver.CLASS, c);
		for(int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		Collection<Map<String, Object>> result = resolver.get(map);
		if(result == null || result.size() == 0) {
			return null;
		}
		if(result.size() > 1) {
			throw new RuntimeException("Enum data exception. More than 1 result.");
		}
		return Enum.valueOf(c, itemToEnumInstanceShortName.itemToEnumInstanceShortName((String) result.toArray(new Map[1])[0].get("item")));
	}
}
