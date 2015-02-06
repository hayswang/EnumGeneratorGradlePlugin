package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.iterable;

/**
 * Created by sky91 on 1/29/15.
 */
public interface SqlBuilder {
	String select(Long start, Long limit);

	String countAll();
}
