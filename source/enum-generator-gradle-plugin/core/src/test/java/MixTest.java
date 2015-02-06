import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import x.flyspace.gradle.plugin.enumgenerator.core.EnumUtilInfo;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.FromItemsGenerator;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.comment.MapToJsonCommentGenerator;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.handler.MapItemsHandler;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable.DatabaseToMapIterable;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable.mysql.MySQLSqlBuilder;
import x.flyspace.gradle.plugin.enumgenerator.runtime.converter.WithPrefixPackageNameAndTypeConverter;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl.ItemToEnumInstanceShortNameDirectlyDefault;
import x.flyspace.gradle.plugin.enumgenerator.runtime.enumconverter.impl.TypeToEnumClassFullNameDirectlyDefault;
import x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap.JsonMapKeyAdapter;
import x.flyspace.gradle.plugin.enumgenerator.runtime.model.TypeFullName;

import java.io.File;
import java.util.*;

/**
 * Created by sky91 on 1/29/15.
 */
public class MixTest extends TestCase {
	public void test() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		PooledDataSource dataSource = new PooledDataSource("com.mysql.jdbc.Driver",
														   "jdbc:mysql://192.168.1.15:3306/hims_test?useUnicode=true&amp;characterEncoding=utf-8",
														   "root",
														   "Manyi@123");
		DatabaseToMapIterable iterable = new DatabaseToMapIterable(dataSource, new MySQLSqlBuilder("iw_dict", "*"));
		Map<String, TypeFullName> generateSetting = new HashMap<>();
		generateSetting.put("code", null);
		generateSetting.put("name", null);
		List<HashSet<String>> indexSetting = Arrays.asList(new HashSet<>(Arrays.asList("type", "item")),
														   new HashSet<>(Arrays.asList("type", "code")),
														   new HashSet<>(Arrays.asList("type", "name")),
														   new HashSet<>(Arrays.asList("type", "code", "name")));
		MapItemsHandler handler = new MapItemsHandler(new File("core/src/test/resources/index"),
													  new File("core/src/test/resources/type"),
													  new File("core/src/test/java"),
													  new TypeToEnumClassFullNameDirectlyDefault(new WithPrefixPackageNameAndTypeConverter("a.b.c",
																																		   ".")),
													  new ItemToEnumInstanceShortNameDirectlyDefault(),
													  generateSetting,
													  indexSetting,
													  new JsonMapKeyAdapter(objectMapper.writer()),
													  new MapToJsonCommentGenerator(objectMapper.writerWithDefaultPrettyPrinter()),
													  new EnumUtilInfo(new TypeFullName("x.a.b.AAA"), "getEnum", "getProperty"));
		FromItemsGenerator<Map<String, Object>> generator = new FromItemsGenerator<>(iterable, handler);
		generator.generate();
	}
}
