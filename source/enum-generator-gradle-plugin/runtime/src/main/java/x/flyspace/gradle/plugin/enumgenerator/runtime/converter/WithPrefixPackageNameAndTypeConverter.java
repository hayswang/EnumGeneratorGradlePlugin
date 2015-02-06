package x.flyspace.gradle.plugin.enumgenerator.runtime.converter;

/**
 * Created by sky91 on 2/3/15.
 */
public class WithPrefixPackageNameAndTypeConverter implements PackageNameAndTypeConverter {
	private String basePackageName;
	private String basePrefix;

	public WithPrefixPackageNameAndTypeConverter(String basePackageName, String basePrefix) {
		this.basePackageName = basePackageName;
		this.basePrefix = basePrefix;
	}

	@Override
	public String packageNameToType(String packageName) {
		return replacePrefix(packageName, basePackageName, basePrefix);
	}

	@Override
	public String typeToPackageName(String type) {
		return replacePrefix(type, basePrefix, basePackageName);
	}

	private String replacePrefix(String s, String origin, String replace) {
		if(!s.startsWith(origin)) {
			throw new RuntimeException(s + " not starts with " + origin);
		}
		String pre = replace == null ? "" : replace;
		while(pre.endsWith(".")) {
			pre = pre.substring(0, pre.length() - 1);
		}
		String post = s.substring(origin.length());
		while(post.startsWith(".")) {
			post = post.substring(1);
		}
		return pre + "." + post;
	}
}
