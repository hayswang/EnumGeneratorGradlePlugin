package a.b.c.iw.agent;

import java.lang.String;
import java.lang.Integer;
import x.a.b.AAA;
import a.b.c.iw.agent.Person;

public enum Person {
/**
	{
  "code" : 2,
  "name" : "郭飞"
}
*/
	gf,
/**
	{
  "code" : 1,
  "name" : "汪海志"
}
*/
	whz,
/**
*/
NULL
;

public static Person from(String[] keys, Object[] values) {
Person e =
AAA.getEnum(Person.class, keys, values);
return e==null?NULL:e;
}

public static Person fromCode
(
			Integer code
			){
return from(
new String[]{
		"code",
	},
new Object[]{
			code,
	}
);
}
public static Person fromName
(
			String name
			){
return from(
new String[]{
		"name",
	},
new Object[]{
			name,
	}
);
}
public static Person fromNameAndCode
(
			String name
		,			Integer code
			){
return from(
new String[]{
		"name",
		"code",
	},
new Object[]{
			name,
			code,
	}
);
}

public Object get(String key){
return AAA.getProperty(this, key);
}

public
	String
	getName() {
return
(String)
	AAA.getProperty(this, "name");
}
public
	Integer
	getCode() {
return
(Integer)
	AAA.getProperty(this, "code");
}

}