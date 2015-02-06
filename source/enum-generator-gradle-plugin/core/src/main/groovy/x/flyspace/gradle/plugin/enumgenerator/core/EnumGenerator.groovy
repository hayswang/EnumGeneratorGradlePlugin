package x.flyspace.gradle.plugin.enumgenerator.core

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by sky91 on 1/28/15.*/
class EnumGenerator implements Plugin<Project> {
	@Override
	void apply(Project target) {
		target.extensions.create('enumGeneratorConfig', Config.class)
		target.task("enumGenerator") << {
			Config config = target.extensions.enumGeneratorConfig
			config.generator.generate()
		}
	}
}
