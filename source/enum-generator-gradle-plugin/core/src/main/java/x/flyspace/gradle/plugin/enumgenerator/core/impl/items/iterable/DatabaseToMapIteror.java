package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by sky91 on 1/29/15.
 */
public class DatabaseToMapIteror implements Iterator<Map<String, Object>> {
	private final SqlBuilder sqlBuilder;
	private final long count;
	private final SqlSessionFactory sqlSessionFactory;
	private long start = 0;

	public DatabaseToMapIteror(SqlSessionFactory sqlSessionFactory, SqlBuilder sqlBuilder) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlBuilder = sqlBuilder;
		try(SqlSession session = sqlSessionFactory.openSession()) {
			MybatisSQLMapper mapper = session.getMapper(MybatisSQLMapper.class);
			count = mapper.countAll(sqlBuilder.countAll());
		}
	}

	@Override
	public boolean hasNext() {
		return start < count;
	}

	@Override
	public Map<String, Object> next() {
		try(SqlSession session = sqlSessionFactory.openSession()) {
			MybatisSQLMapper mapper = session.getMapper(MybatisSQLMapper.class);
			return mapper.selectMapList(sqlBuilder.select(start++, 1L)).get(0);
		}
	}

	@Override
	public void remove() {
		throw new RuntimeException("Not supported.");
	}
}