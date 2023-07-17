package com.bosch.library.library.entities.criteria;

import java.util.Objects;

public class BookCriteria {
    private String title;
    private String author;
    private String category;
    private Integer quantity;

    public BookCriteria() {
    }

    public BookCriteria(final String title, final String author, final String category, final Integer quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return Objects.equals(this.title, that.title) && Objects.equals(this.author, that.author) && Objects.equals(this.category, that.category) && Objects.equals(this.quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author, this.category, this.quantity);
    }

    @Override
    public String toString() {
        return "BookCriteria{" +
                "title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", category='" + this.category + '\'' +
                ", quantity=" + this.quantity +
                '}';
    }
}
