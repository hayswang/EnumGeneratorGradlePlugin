package x.flyspace.gradle.plugin.enumgenerator.core.impl.items.handler;

import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.HandleFilter;
import x.flyspace.gradle.plugin.enumgenerator.core.impl.items.ItemsHandler;

/**
 * Created by sky91 on 2/5/15.
 */
public class FilteredHandler<T> implements ItemsHandler<T> {
	private ItemsHandler<T> innerHandler;
	private HandleFilter<T> handleFilter;

	public FilteredHandler(ItemsHandler<T> innerHandler, HandleFilter<T> handleFilter) {
		this.innerHandler = innerHandler;
		this.handleFilter = handleFilter;
	}

	@Override
	public void handle(T item) {
		if(handleFilter.shouldHandle(item)) {
			innerHandler.handle(item);
		}
	}

	@Override
	public void finishHandle() {
		innerHandler.finishHandle();
	}
}
