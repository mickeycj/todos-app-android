package ssd.project.mickeycj.todosapp.model;

import java.util.Date;

/**
 * Created by user on 4/6/60.
 */

public class Todo {

    private String title;
    private boolean importance;
    private Date createdAt;

    public Todo() {}

    public Todo(String title, boolean importance) {
        this.title = title;
        this.importance = importance;
        this.createdAt = new Date();
    }

    public String getTitle() { return title; }

    public boolean isImportant() { return importance; }

    public Date getCreatedAt() { return createdAt; }

    public void setTitle(String title) { this.title = title; }

    public void setImportance(boolean importance) { this.importance = importance; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
