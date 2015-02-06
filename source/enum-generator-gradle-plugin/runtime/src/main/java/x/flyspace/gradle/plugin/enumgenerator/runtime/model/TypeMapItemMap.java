package x.flyspace.gradle.plugin.enumgenerator.runtime.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by sky91 on 2/5/15.
 */
public class TypeMapItemMap implements Serializable {
	private Map<String, Map<String, String>> itemMap;
	private Map<String, String> typeMap;

	public TypeMapItemMap() {
	}

	public TypeMapItemMap(Map<String, Map<String, String>> itemMap, Map<String, String> typeMap) {
		this.itemMap = itemMap;
		this.typeMap = typeMap;
	}

	public Map<String, Map<String, String>> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, Map<String, String>> itemMap) {
		this.itemMap = itemMap;
	}

	public Map<String, String> getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}
}
