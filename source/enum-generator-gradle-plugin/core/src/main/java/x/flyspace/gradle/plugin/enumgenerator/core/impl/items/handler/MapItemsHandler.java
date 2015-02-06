package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.handler;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import x.flyspace.gradle.plugin.enumgenerator.core.EnumUtilInfo;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.CommentGenerator;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.ItemsHandler;
import x.flyspace.gradle.plugin.enumgenerator.runtime.converter.ObjectToStream;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.ItemToEnumInstanceShortName;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.TypeToEnumClassFullName;
import x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap.KeyAdapter;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.FileFullName;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.MemoryIndex;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeFullName;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeMapItemMap;
import x.flyspace.gradle.plugin.enumgenerator.runtime.util.MixUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by sky91 on 1/30/15.
 */
public class MapItemsHandler implements ItemsHandler<Map<String, Object>> {
	private static final MixUtil MIX_UTIL = new MixUtil();
	private final File indexFile;
	private final File typeMapFile;
	private final File sourceDir;
	private final TypeToEnumClassFullName typeToEnumClassFullName;
	private final ItemToEnumInstanceShortName itemToEnumInstanceShortName;
	private final Map<String, TypeFullName> generateSettings;
	private final Collection<? extends Set<String>> indexSettings;
	private final KeyAdapter<String, Map<String, Object>> keyAdapter;
	private final CommentGenerator<Map<String, Object>> commentGenerator;
	private final EnumUtilInfo enumUtilInfo;
	private final MemoryIndex index;
	private final Map<String, Map<String, String>> itemMap;
	private final Map<String, String> typeMap;
	private final VelocityEngine velocityEngine;
	private final UsedClassesMap usedClasses;
	private final Map<String, Map<String, String>> itemCommentMap;

	public MapItemsHandler(File indexFile,
						   File typeMapFile,
						   File sourceDir,
						   TypeToEnumClassFullName typeToEnumClassFullName,
						   ItemToEnumInstanceShortName itemToEnumInstanceShortName,
						   Map<String, TypeFullName> generateSettings,
						   Collection<? extends Set<String>> indexSettings,
						   KeyAdapter<String, Map<String, Object>> keyAdapter,
						   CommentGenerator<Map<String, Object>> commentGenerator,
						   EnumUtilInfo enumUtilInfo) {
		this.indexFile = indexFile;
		this.typeMapFile = typeMapFile;
		this.sourceDir = sourceDir;
		this.typeToEnumClassFullName = typeToEnumClassFullName;
		this.itemToEnumInstanceShortName = itemToEnumInstanceShortName;
		this.generateSettings = new HashMap<>(generateSettings);
		this.indexSettings = indexSettings;
		this.keyAdapter = keyAdapter;
		this.commentGenerator = commentGenerator;
		this.enumUtilInfo = enumUtilInfo;
		index = new MemoryIndex();
		itemMap = new HashMap<>();
		typeMap = new HashMap<>();
		Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/velocity/velocity.properties"));
			velocityEngine = new VelocityEngine(properties);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		usedClasses = new UsedClassesMap();
		itemCommentMap = new HashMap<>();
	}

	@Override
	public void handle(Map<String, Object> item) {
		for(String key : Collections.unmodifiableSet(generateSettings.keySet())) {
			if(generateSettings.get(key) == null) {
				generateSettings.put(key, new TypeFullName(item.get(key).getClass()));
			}
		}
		for(Set<String> indexSetting : indexSettings) {
			Map<String, Object> indexKeyMap = new TreeMap<>();
			for(String keyString : indexSetting) {
				indexKeyMap.put(keyString, item.get(keyString));
			}
			String indexKey = keyAdapter.key(indexKeyMap);
			Collection<Map<String, Object>> o = index.get(indexKey);
			if(o == null) {
				o = new LinkedList<>();
				index.put(indexKey, o);
			}
			o.add(item);
		}
		//
		String classFullName = typeToEnumClassFullName.typeToEnumClassFullName(item.get("type").toString());
		Map<String, String> enumMap = itemMap.get(classFullName);
		if(enumMap == null) {
			enumMap = new HashMap<>();
			itemMap.put(classFullName, enumMap);
		}
		String instanceShortName = itemToEnumInstanceShortName.itemToEnumInstanceShortName(item.get("item").toString());
		enumMap.put(instanceShortName, item.get("item").toString());
		typeMap.put(classFullName, item.get("type").toString());
		//
		Map<String, String> commentMap = itemCommentMap.get(classFullName);
		if(commentMap == null) {
			commentMap = new HashMap<>();
			itemCommentMap.put(classFullName, commentMap);
		}
		Map<String, Object> commentSource = new HashMap<>(generateSettings.size());
		for(String k : generateSettings.keySet()) {
			commentSource.put(k, item.get(k));
		}
		commentMap.put(instanceShortName, commentGenerator.generateComment(commentSource));
	}

	@Override
	public void finishHandle() {
		sourceDir.mkdirs();
		//
		for(TypeFullName type : generateSettings.values()) {
			usedClasses.put(type.getTypeShortName(), type);
		}
		usedClasses.put("enumUtil", enumUtilInfo.getTypeFullName());
		//
		for(Entry<String, Map<String, String>> classEntry : itemMap.entrySet()) {
			UsedClassesMap usedClassesMap = new UsedClassesMap(usedClasses);
			TypeFullName typeFullName = new TypeFullName(classEntry.getKey());
			usedClassesMap.put("enumClass", typeFullName);
			List<Property> properties = new LinkedList<>();
			for(Entry<String, TypeFullName> entry : generateSettings.entrySet()) {
				Property property = new Property();
				property.setName(entry.getKey());
				properties.add(property);
			}
			List<EnumItem> enumItems = new ArrayList<>(classEntry.getValue().size());
			for(String enumInstanceName : classEntry.getValue().keySet()) {
				enumItems.add(new EnumItem(enumInstanceName, itemCommentMap.get(classEntry.getKey()).get(enumInstanceName)));
			}
			List<List<String>> fromMethods = new LinkedList<>();
			for(Set<String> indexSetting : indexSettings) {
				Set<String> shouldDo = new LinkedHashSet<>(indexSetting);
				shouldDo.remove("type");
				shouldDo.remove("item");
				if(shouldDo.size() > 0) {
					fromMethods.add(new ArrayList<>(shouldDo));
				}
			}
			//
			FileFullName fileFullName = new FileFullName(typeFullName, "java");
			File javaFile = new File(sourceDir, fileFullName.getFileFullName());
			javaFile.getParentFile().mkdirs();
			try {
				javaFile.createNewFile();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
			try(FileWriter fileWriter = new FileWriter(javaFile, false)) {
				Template template = velocityEngine.getTemplate("EnumClass.vm");
				VelocityContext context = new VelocityContext();
				context.put("mixUtil", MIX_UTIL);
				context.put("usedClasses", usedClassesMap);
				context.put("enumUtilInfo", enumUtilInfo);
				context.put("enumItems", enumItems);
				context.put("properties", properties);
				context.put("generateSettings", generateSettings);
				context.put("fromMethods", fromMethods);
				template.merge(context, fileWriter);
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		indexFile.getParentFile().mkdirs();
		try {
			indexFile.createNewFile();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		try(FileOutputStream outputStream = new FileOutputStream(indexFile)) {
			new ObjectToStream().convert(index, outputStream);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		typeMapFile.getParentFile().mkdirs();
		try {
			typeMapFile.createNewFile();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		try(FileOutputStream outputStream = new FileOutputStream(typeMapFile)) {
			new ObjectToStream().convert(new TypeMapItemMap(itemMap, typeMap), outputStream);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static class EnumItem {
		private String name;
		private String comment;

		public EnumItem(String name, String comment) {
			this.name = name;
			this.comment = comment;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}
	}

	public static class Property {
		private String name;
		private static final MixUtil mixUtil = new MixUtil();

		public String getGetMethodName() {
			return "get" + mixUtil.bigCamel(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private class UsedClassesMap extends LinkedHashMap<String, TypeFullName> {
		private UsedClassesMap(int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor);
		}

		private UsedClassesMap(int initialCapacity) {
			super(initialCapacity);
		}

		private UsedClassesMap() {
		}

		private UsedClassesMap(Map<? extends String, ? extends TypeFullName> m) {
			super(m);
		}

		private UsedClassesMap(int initialCapacity, float loadFactor, boolean accessOrder) {
			super(initialCapacity, loadFactor, accessOrder);
		}

		@Override
		public TypeFullName put(String key, TypeFullName value) {
			TypeFullName origin = get(key);
			if(origin != null && !value.equals(origin)) {
				throw new RuntimeException("Duplicated class short name: " + key + "->" + origin + "? " + key + "->" + value + "?");
			}
			return super.put(key, value);
		}
	}
}
