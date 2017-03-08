package br.com.neotech.framework.lazy;

import java.util.List;

/**
 * Interface for lists that support paging
 *
 */
public interface SelectablePaginatedList<T extends SelectableObject> extends List<T> {

    List<T> getSelection();
    void clearSelection();
    void select(T obj);

}