package x.flyspace.gradle.plugin.enumgenerator.runtime;

/**
 * Created by sky91 on 2/2/15.
 */
public interface Resolver<Result, Param> {
	Result get(Param param);
}
