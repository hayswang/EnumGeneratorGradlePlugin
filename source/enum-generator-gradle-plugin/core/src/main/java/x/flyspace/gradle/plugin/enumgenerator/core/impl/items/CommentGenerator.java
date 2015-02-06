package x.flyspace.gradle.plugin.enumgenerator.core.impl.items;

/**
 * Created by sky91 on 2/6/15.
 */
public interface CommentGenerator<T> {
	String generateComment(T param);
}
