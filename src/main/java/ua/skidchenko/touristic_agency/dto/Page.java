package ua.skidchenko.touristic_agency.dto;

import java.util.Collections;
import java.util.List;

public class Page <T>{
    private List<T> content;
    private int amountOfPages;
    private int currentPage;

    public Page(List<T> content, int amountOfPages, int currentPage) {
        this.content = content;
        this.amountOfPages = amountOfPages;
        this.currentPage = currentPage;
    }

    public static <T> Page<T> empty() {
        return new Page<>(Collections.emptyList(), 0, 0);
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getAmountOfPages() {
        return amountOfPages;
    }

    public void setAmountOfPages(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
