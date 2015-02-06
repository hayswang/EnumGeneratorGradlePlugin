package x.flyspace.gradle.plugin.enumgenerator.core.impl.items;

import x.flyspace.gradle.plugin.enumgenerator.core.AbstractEnumFileGenerator;

/**
 * Created by sky91 on 1/29/15.
 */
public class FromItemsGenerator<T> extends AbstractEnumFileGenerator {
	private final Iterable<T> items;
	private final ItemsHandler<T> handler;

	public FromItemsGenerator(Iterable<T> items, ItemsHandler<T> handler) {
		this.items = items;
		this.handler = handler;
	}

	@Override
	public void generate() {
		for(T item : items) {
			handler.handle(item);
		}
		handler.finishHandle();
	}
}
