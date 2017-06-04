package ssd.project.mickeycj.todosapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 4/6/60.
 */

public class Todo {

    private String title;
    private boolean importance;
    private Date createdAt;
    private List<Item> itemList;

    public Todo(String title, boolean importance, Date createdAt) {
        this.title = title;
        this.importance = importance;
        this.createdAt = (createdAt != null) ? createdAt : new Date();
        this.itemList = new ArrayList<>();
    }

    public Todo(String title, boolean importance) { this(title, importance, null); }

    public Todo() {}

    public String getTitle() { return title; }

    public boolean isImportant() { return importance; }

    public Date getCreatedAt() { return createdAt; }

    public List<Item> getItemList() { return itemList; }

    public void setTitle(String title) { this.title = title; }

    public void setImportance(boolean importance) { this.importance = importance; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public void setItemList(List<Item> itemList) { this.itemList = itemList; }

    public Item getItem(int index) { return itemList.get(index); }

    public int numItems() { return itemList.size(); }

    public int getProgress() {
        int doneCount = 0;
        for (Item item : itemList) {
            if (item.isDone()) {
                doneCount++;
            }
        }
        return doneCount;
    }
}
