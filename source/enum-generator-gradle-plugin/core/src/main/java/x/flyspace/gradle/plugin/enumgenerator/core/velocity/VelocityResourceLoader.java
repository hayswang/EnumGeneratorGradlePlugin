package x.flyspace.gradle.plugin.enumgenerator.core.velocity;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sky91 on 1/22/15.
 */
public class VelocityResourceLoader extends ClasspathResourceLoader {
	private ArrayList<String> paths = new ArrayList<>();
	private boolean unicode = false;

	/**
	 * This is abstract in the base class, so we need it
	 *
	 * @param configuration
	 */
	@Override
	public void init(ExtendedProperties configuration) {
		if(log.isTraceEnabled()) {
			log.trace("FileResourceLoader : initialization starting.");
		}
		paths.addAll(configuration.getVector("path"));
		// unicode files may have a BOM marker at the start, but Java
		// has problems recognizing the UTF-8 bom. Enabling unicode will
		// recognize all unicode boms.
		unicode = configuration.getBoolean("unicode", false);
		if(log.isDebugEnabled()) {
			log.debug("Do unicode file recognition:  " + unicode);
		}
		if(log.isDebugEnabled()) {
			// trim spaces from all paths
			StringUtils.trimStrings(paths);
			// this section lets tell people what paths we will be using
			int sz = paths.size();
			for(int i = 0; i < sz; i++) {
				log.debug("FileResourceLoader : adding path '" + (String) paths.get(i) + "'");
			}
			log.trace("FileResourceLoader : initialization complete.");
		}
	}

	/**
	 * Get an InputStream so that the Runtime can build a
	 * template with it.
	 *
	 * @param name name of template to get
	 * @return InputStream containing the template
	 * @throws org.apache.velocity.exception.ResourceNotFoundException if template not found
	 *                                                                 in  classpath.
	 */
	@Override
	public InputStream getResourceStream(String name) throws ResourceNotFoundException {
		for(String path : paths) {
			try {
				return super.getResourceStream(path + name);
			} catch(ResourceNotFoundException e) {
			}
		}
		String msg = "ClasspathResourceLoader Error: cannot find resource " + name;
		throw new ResourceNotFoundException(msg);
	}
}
