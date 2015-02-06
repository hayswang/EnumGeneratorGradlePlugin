package x.flyspace.gradle.plugin.enumgenerator.runtime.memorymap;

/**
 * Created by sky91 on 2/4/15.
 */
public interface KeyAdapter<K, P> {
	K key(P param);
}
