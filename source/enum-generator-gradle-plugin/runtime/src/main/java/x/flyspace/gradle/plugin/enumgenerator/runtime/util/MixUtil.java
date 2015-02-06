package x.flyspace.gradle.plugin.enumgenerator.runtime.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky91 on 2/6/15.
 */
public class MixUtil {
	public String smallCamel(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public String bigCamel(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public String stringJoin(List<String> ss, String connector) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < ss.size() - 1; i++) {
			builder.append(ss.get(i));
			builder.append(connector);
		}
		builder.append(ss.get(ss.size() - 1));
		return builder.toString();
	}

	public String bigCamelJoin(List<String> ss, String connector) {
		ArrayList<String> list = new ArrayList<>(ss.size());
		for(String s : ss) {
			list.add(bigCamel(s));
		}
		return stringJoin(list, connector);
	}
}
