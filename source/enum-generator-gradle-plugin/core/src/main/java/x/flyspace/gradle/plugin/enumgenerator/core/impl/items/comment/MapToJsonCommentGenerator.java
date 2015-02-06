package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.CommentGenerator;

import java.util.Map;

/**
 * Created by sky91 on 2/6/15.
 */
public class MapToJsonCommentGenerator implements CommentGenerator<Map<String, Object>> {
	private final ObjectWriter objectWriter;

	public MapToJsonCommentGenerator(ObjectWriter objectWriter) {
		this.objectWriter = objectWriter;
	}

	@Override
	public String generateComment(Map<String, Object> param) {
		try {
			return objectWriter.writeValueAsString(param);
		} catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
