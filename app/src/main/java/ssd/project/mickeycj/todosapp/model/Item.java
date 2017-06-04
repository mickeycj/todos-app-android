package ssd.project.mickeycj.todosapp.model;

import java.util.Date;

/**
 * Created by user on 4/6/60.
 */

public class Item {

    private String title;
    private boolean done;
    private Date createdAt;

    public Item(String title, boolean done, Date createdAt) {
        this.title = title;
        this.done = done;
        this.createdAt = (createdAt != null) ? createdAt : new Date();
    }

    public Item(String title) { this(title, false, null); }

    public Item() {}

    public String getTitle() { return title; }

    public boolean isDone() { return done; }

    public Date getCreatedAt() { return createdAt; }

    public void setTitle(String title) { this.title = title; }

    public void markAsDone() { done = true; }

    public void markAsNotDone() { done = false; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
