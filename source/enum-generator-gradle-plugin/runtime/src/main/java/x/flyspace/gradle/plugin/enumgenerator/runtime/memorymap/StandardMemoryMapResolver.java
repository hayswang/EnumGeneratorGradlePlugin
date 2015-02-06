package x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap;

import x.flyspace.gradle.plugin.enumgenerator.runtime.Resolver;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.MemoryIndex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by sky91 on 2/2/15.
 */
public class StandardMemoryMapResolver implements Resolver<Collection<Map<String, Object>>, Map<String, Object>> {
	private final MemoryIndex index;
	private final KeyAdapter<String, Map<String, Object>> keyAdapter;

	public StandardMemoryMapResolver(MemoryIndex index, KeyAdapter<String, Map<String, Object>> keyAdapter) {
		this.index = index;
		this.keyAdapter = keyAdapter;
	}

	@Override
	public Collection<Map<String, Object>> get(Map<String, Object> param) {
		Collection<Map<String, Object>> result = index.get(keyAdapter.key(param));
		return result == null ? new ArrayList<Map<String, Object>>(0) : result;
	}
}
