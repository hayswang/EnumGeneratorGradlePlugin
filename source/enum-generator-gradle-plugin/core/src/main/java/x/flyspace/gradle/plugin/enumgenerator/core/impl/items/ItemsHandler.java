package x.flyspace.gradle.plugin.enumgenerator.core.impl.items;

/**
 * Created by sky91 on 1/29/15.
 */
public interface ItemsHandler<T> {
	void handle(T item);

	void finishHandle();
}
