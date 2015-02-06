package a.b.c.iw.agent;

import x.a.b.AAA;

public enum Gender {
	/**
	 * {
	 * "code" : 2,
	 * "name" : "女"
	 * }
	 */
	female,
	/**
	 * {
	 * "code" : 1,
	 * "name" : "男"
	 * }
	 */
	male,
	/**
	 * {
	 * "code" : 3,
	 * "name" : "未知"
	 * }
	 */
	unknown,
	/**
	 */
	NULL;

	public static Gender from(String[] keys, Object[] values) {
		Gender e = AAA.getEnum(Gender.class, keys, values);
		return e == null ? NULL : e;
	}

	public static Gender fromCode(Integer code) {
		return from(new String[]{"code",}, new Object[]{code,});
	}

	public static Gender fromName(String name) {
		return from(new String[]{"name",}, new Object[]{name,});
	}

	public static Gender fromNameAndCode(String name, Integer code) {
		return from(new String[]{"name", "code",}, new Object[]{name, code,});
	}

	public Object get(String key) {
		return AAA.getProperty(this, key);
	}

	public String getName() {
		return (String) AAA.getProperty(this, "name");
	}

	public Integer getCode() {
		return (Integer) AAA.getProperty(this, "code");
	}
}