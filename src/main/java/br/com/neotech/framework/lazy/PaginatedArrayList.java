package br.com.neotech.framework.lazy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of PaginatedList backed by an ArrayList
 *
 */
public abstract class PaginatedArrayList<T> implements PaginatedList<T> {

    @Getter @Setter
    private Object datasource;

    private List<T> page;
    private int size;
    private int pageSize = 20;
    private int pageIndex = 0;
    private int totalSize = -1;

    private String sortField;
    private SortOrder sortOrder;

    public PaginatedArrayList(Object datasource, int pageSize) {
        this.datasource = datasource;
        this.pageSize = pageSize;
        this.totalSize = -1;
    }

    public abstract List<T> load(int first, int pageSize);
    public abstract int count();

    @Override
    public void init() {
        repaginate();
    }

    private void repaginate() {

        int start = 1;
        if (totalSize < 0) {
            // lista não carregada ainda
            pageIndex = 0;

        } else {

            // o primeiro registro da lista será o primeiro registro da página
            start = pageIndex * pageSize + 1;

            if (start >= totalSize) {
                pageIndex = getNumberOfPages() -1;
                start = pageIndex * pageSize + 1;

            } else if (start < 0) {
                pageIndex = 0;
                start = 1;
            }
        }

        page = load(start, pageSize);
        size = page.size();
        totalSize = count();

    }

    /* List accessors (uses page) */

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return page.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return page.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    public Object[] toArray() {
        return page.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object[] toArray(Object[] a) {
        return page.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return page.containsAll(c);
    }

    @Override
    public T get(int index) {
        return page.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return page.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return page.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return page.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return page.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return page.subList(fromIndex, toIndex);
    }

    /* List mutators (uses master list) */

    @Override
    public boolean add(T o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    /* Paginated List methods */

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public boolean isFirstPage() {
        return pageIndex == 0;
    }

    @Override
    public boolean isLastPage() {
        return pageIndex == getNumberOfPages();
    }

    @Override
    public boolean isMiddlePage() {
        return !(isFirstPage() || isLastPage());
    }

    @Override
    public boolean isNextPageAvailable() {
        return !isLastPage();
    }

    @Override
    public boolean isPreviousPageAvailable() {
        return !isFirstPage();
    }

    @Override
    public void gotoFirstPage() {
        if (getTotalSize() > 0 && pageIndex != 0) {
            pageIndex = 0;
            repaginate();
        }
    }

    @Override
    public void gotoPreviousPage() {
        if (isPreviousPageAvailable()) {
            pageIndex--;
            repaginate();
        }
    }

    @Override
    public void gotoNextPage() {
        if (isNextPageAvailable()) {
            pageIndex++;
            repaginate();
        }
    }

    @Override
    public void gotoLastPage() {
        if (getTotalSize() > 0 && pageIndex != getNumberOfPages() -1) {
            pageIndex = getNumberOfPages() - 1;
            repaginate();
        }
    }

    @Override
    public void gotoPage(int pageNumber) {
        pageIndex = pageNumber;
        repaginate();
    }

    @Override
    public int getPageIndex() {
        return pageIndex + 1;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    @Override
    public int getNumberOfPages() {
        int np = totalSize / pageSize;
        if(totalSize % pageSize > 0) {
            np++;
        }
        return np;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        repaginate();
    }

    @Override
    public void setSortField(String sortField) {
        if(sortField != null) {
            SortOrder so = null;
            if(sortOrder == null || sortOrder == SortOrder.DESCENDING) {
                so = SortOrder.ASCENDING;
            } else if(sortOrder == SortOrder.ASCENDING) {
                so = SortOrder.DESCENDING;
            }
            setSortField(sortField, so);
        }
    }

    @Override
    public void setSortField(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        repaginate();
    }

    @Override
    public String getSortField() {
        return sortField;
    }

    @Override
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    protected List<T> getPage() {
        return page;
    }
}
