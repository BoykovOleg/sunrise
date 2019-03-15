package dev.sunrise.application;

import org.springframework.data.domain.Page;

import java.util.List;

public class SearchResult<T> {
    private final long total;
    private final int totalPages;
    private final int page;
    private final int perPage;
    private final List<T> content;

    static <T> SearchResult<T> createFromPage(Page<T> page) {
        return new SearchResult<>(page);
    }

    private SearchResult(Page<T> page) {
        this.total = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
        this.perPage = page.getSize();
        this.content = page.getContent();
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public List<T> getContent() {
        return content;
    }
}
