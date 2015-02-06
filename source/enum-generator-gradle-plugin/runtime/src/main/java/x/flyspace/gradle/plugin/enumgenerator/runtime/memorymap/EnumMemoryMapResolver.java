package x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap;

import x.flyspace.gradle.plugin.enumgenerator.runtime.Resolver;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumClassToType;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.EnumInstanceToItem;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sky91 on 2/5/15.
 */
public class EnumMemoryMapResolver implements Resolver<Collection<Map<String, Object>>, Map<String, Object>> {
	public static final String CLASS = "enumClass";
	public static final String INSTANCE = "enumInstance";
	private final Resolver<Collection<Map<String, Object>>, Map<String, Object>> parentResolver;
	private final EnumClassToType enumClassToType;
	private final EnumInstanceToItem enumInstanceToItem;

	public EnumMemoryMapResolver(Resolver<Collection<Map<String, Object>>, Map<String, Object>> parentResolver,
								 EnumClassToType enumClassToType,
								 EnumInstanceToItem enumInstanceToItem) {
		this.parentResolver = parentResolver;
		this.enumClassToType = enumClassToType;
		this.enumInstanceToItem = enumInstanceToItem;
	}

	@Override
	public Collection<Map<String, Object>> get(Map<String, Object> param) {
		if(param.containsKey(CLASS)) {
			param.put("type", enumClassToType.enumClassToType((Class) param.get(CLASS)));
			param.remove(CLASS);
		}
		if(param.containsKey(INSTANCE)) {
			param.put("item", enumInstanceToItem.enumInstanceToItem((Enum) param.get(INSTANCE)));
			param.remove(INSTANCE);
		}
		return parentResolver.get(param);
	}
}
