package x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sky91 on 2/4/15.
 */
public class JsonMapKeyAdapter implements JsonKeyAdapter<Map<String, Object>> {
	private final ObjectWriter objectWriter;

	public JsonMapKeyAdapter(ObjectWriter objectWriter) {
		this.objectWriter = objectWriter;
	}

	@Override
	public String key(Map<String, Object> param) {
		try {
			return objectWriter.writeValueAsString(new TreeMap<>(param));
		} catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
