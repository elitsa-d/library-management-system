package com.bosch.library.library.entities.dto;

import java.util.Objects;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String category;

    public BookDTO() {
    }

    public BookDTO(final String title, final String author, final String category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public BookDTO(final Long id, final String title, final String author, final String category) {
        this(title, author, category);
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookDTO bookDTO = (BookDTO) o;
        return this.title.equals(bookDTO.title) && this.author.equals(bookDTO.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", category='" + this.category + '\'' +
                '}';
    }
}
