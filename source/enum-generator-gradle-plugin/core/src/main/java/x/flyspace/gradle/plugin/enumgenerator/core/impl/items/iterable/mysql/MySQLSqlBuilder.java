package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable.mysql;

import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable.SqlBuilder;

/**
 * Created by sky91 on 1/29/15.
 */
public class MySQLSqlBuilder implements SqlBuilder {
	public MySQLSqlBuilder(String tableName, String selectColumns) {
		this.tableName = tableName;
		this.selectColumns = selectColumns;
	}

	private final String tableName;
	private final String selectColumns;

	@Override
	public String select(Long start, Long limit) {
		return "select " + selectColumns + " from " + tableName + (start == null || limit == null ? "" : (" limit " + start + "," + limit)) + ";";
	}

	@Override
	public String countAll() {
		return "select count(1) from " + tableName + ";";
	}
}
