package x.flyspace.gradle.plugin.enumgenerator.core.impl.items;

/**
 * Created by sky91 on 2/5/15.
 */
public interface HandleFilter<T> {
	boolean shouldHandle(T item);
}
