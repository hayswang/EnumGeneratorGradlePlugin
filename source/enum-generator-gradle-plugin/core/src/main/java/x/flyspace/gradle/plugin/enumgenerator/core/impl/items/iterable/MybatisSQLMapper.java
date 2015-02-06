package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by sky91 on 1/29/15.
 */
public interface MybatisSQLMapper {
	@Select("${statement}")
	List<Map<String, Object>> selectMapList(@Param("statement") String statement);

	@Select("${statement}")
	long countAll(@Param("statement") String statement);
}
