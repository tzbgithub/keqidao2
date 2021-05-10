package com.qidao.application.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Guo
 */
public class Page<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = -3720998571176536865L;
    private List<T> content = new ArrayList<>();  // NOSONAR
    private long total;
    private int pageNum;
    private int pageSize;

    public Page() {
    }

    public Page(List<T> content, long total, int pageNum, int pageSize) {
        this.content = content;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    /**
     * @return if there is a previous page.
     */
    public boolean hasPrevious() {
        return getPageNum() > 1;
    }

    /**
     * @return if there is a next page.
     */
    public boolean hasNext() {
        return getPageNum() < getTotalPage();
    }

    /**
     * @return whether the current page is the first one.
     */
    public boolean isFirst() {
        return !hasPrevious();
    }

    /**
     * @return whether the current  page is the last one.
     */
    boolean isLast() {
        return !hasNext();
    }

    /**
     * @return the total amount of elements of all pages.
     */
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * @return the number of total pages
     */
    public int getTotalPage() {
        return getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageSize());
    }

    /**
     * @return Returns the page content as unmodifiable {@link List}
     */
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * @return whether the current page has content.
     */
    public boolean hasContent() {
        return getContentSize() > 0;
    }

    /**
     * @return the number of elements on current page.
     */
    public int getContentSize() {
        return content.size();
    }

    /**
     * @return the number of items of each page
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the number of current page.
     */
    public int getPageNum() {
        return pageNum == 0 ? 1 : pageNum;
    }

    /**
     * Set the number of current page. (Zero-based numbering.)
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public Iterator<T> iterator() {
        return getContent().iterator();
    }


}
