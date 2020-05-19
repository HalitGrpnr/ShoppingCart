package org.project.domain;

public class Category {
    private Long id;
    private Long parentId;
    private String title;

    public Category(String title) {
        this.title = title;
    }

    public Category(Long parentId, String title) {
        this.parentId = parentId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                '}';
    }
}
