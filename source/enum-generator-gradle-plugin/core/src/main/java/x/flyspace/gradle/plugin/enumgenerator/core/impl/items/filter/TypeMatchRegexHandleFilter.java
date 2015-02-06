package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.filter;

import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.HandleFilter;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by sky91 on 2/5/15.
 */
public class TypeMatchRegexHandleFilter implements HandleFilter<Map<String, Object>> {
	private Pattern pattern;

	public TypeMatchRegexHandleFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override
	public boolean shouldHandle(Map<String, Object> item) {
		if(item == null || item.get("type") == null) {
			return false;
		}
		return pattern.matcher(item.get("type").toString()).matches();
	}
}
