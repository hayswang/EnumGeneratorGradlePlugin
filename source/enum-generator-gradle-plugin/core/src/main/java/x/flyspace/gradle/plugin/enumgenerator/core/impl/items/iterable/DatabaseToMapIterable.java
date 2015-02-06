package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sky91 on 1/29/15.
 */
public class DatabaseToMapIterable implements Iterable<Map<String, Object>> {
	private static final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	private final SqlSessionFactory sqlSessionFactory;
	private final SqlBuilder sqlBuilder;

	public DatabaseToMapIterable(DataSource dataSource, SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
		Configuration configuration = new Configuration(new Environment(toString(), new JdbcTransactionFactory(), dataSource));
		configuration.addMapper(MybatisSQLMapper.class);
		sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration);
	}

	@Override
	public Iterator<Map<String, Object>> iterator() {
		return new DatabaseToMapIteror(sqlSessionFactory, sqlBuilder);
	}
}
