package ssd.project.mickeycj.todosapp.model;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 4/6/60.
 */

public class Repository {

    private static Repository currentRepository;

    public static Repository getCurrentRepository(String user) {
        if (currentRepository == null || !currentRepository.getCurrentUser().equals("user")) {
            currentRepository = new Repository(user);
        }
        return currentRepository;
    }

    public static List<Todo> getCurrentTodoList() { return currentRepository.getTodoList(); }

    public static Todo getTodoFromCurrentTodoList(int todoIndex) { return currentRepository.getTodo(todoIndex); }

    public static List<Item> getCurrentItemListFromCurrentTodo(int todoIndex) { return currentRepository.getItemListOf(todoIndex); }

    public static Item getCurrentItemFromCurrentTodo(int todoIndex, int itemIndex) { return currentRepository.getItemFrom(todoIndex, itemIndex); }

    public static void addNewTodoToCurrentTodoList(Todo todo) { currentRepository.addNewTodo(todo); }

    public static void removeTodoFromCurrentTodoList(String title) { currentRepository.removeTodo(title); }

    public static void removeTodoFromCurrentTodoList(int todoIndex) { removeTodoFromCurrentTodoList(currentRepository.getTodo(todoIndex).getTitle()); }

    public static void addNewItemToCurrentItemListInCurrentTodo(int todoIndex, Item item) { currentRepository.addNewItem(todoIndex, item); }

    public static void relocateItemsInCurrentItemListFromCurrentTodo(String title, List<Item> itemList) { currentRepository.relocateItems(title, itemList); }

    public static void removeItemFromCurrentItemListInCurrentTodo(int todoIndex, String title) { currentRepository.removeItem(todoIndex, title); }

    public static void removeItemFromCurrentItemLsitInCurrentTodo(int todoIndex, int itemIndex) { removeItemFromCurrentItemListInCurrentTodo(todoIndex, currentRepository.getItemFrom(todoIndex, itemIndex).getTitle()); }

    public static void setItemDoneFromCurrentItemlistInCurrentTodo(int todoIndex, int itemIndex, boolean done) { currentRepository.setItemDone(todoIndex, itemIndex, done); }

    private DatabaseReference todoListDatabaseReference;
    private DatabaseReference itemListDatabaseReference;

    private String currentUser;

    private List<Todo> todoList;

    private Repository(String user) {
        currentUser = user;
        todoList = new ArrayList<>();
        todoListDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user).child("todo_list");
        todoListDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String title = "";
                boolean importance = false;
                Date createdAt = null;
                try {
                    title = dataSnapshot.getKey().replace('_', ' ');
                    importance = dataSnapshot.child("importance").getValue(Boolean.class);
                    createdAt = dataSnapshot.child("created_at").getValue(Date.class);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                final Todo todo = new Todo(title, importance, createdAt);
                itemListDatabaseReference = dataSnapshot.child("item_list").getRef();
                itemListDatabaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String title = "";
                        boolean done = false;
                        Date createdAt = null;
                        try {
                            title = dataSnapshot.getKey().replace('_', ' ');
                            done = dataSnapshot.child("is_done").getValue(Boolean.class);
                            createdAt = dataSnapshot.child("created_at").getValue(Date.class);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        todo.addItem(new Item(title, done, createdAt));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) { }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
                todoList.add(todo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private String getCurrentUser() { return currentUser; }

    private List<Todo> getTodoList() { return todoList; }

    private Todo getTodo(int todoIndex) { return todoList.get(todoIndex); }

    private List<Item> getItemListOf(int todoIndex) { return todoList.get(todoIndex).getItemList(); }

    private Item getItemFrom(int todoIndex, int itemIndex) { return todoList.get(todoIndex).getItemList().get(itemIndex); }

    private void addNewTodo(Todo todo) {
        todoListDatabaseReference.child(todo.getTitle().replace(' ', '_')).child("importance").setValue(todo.isImportant());
        todoListDatabaseReference.child(todo.getTitle().replace(' ', '_')).child("created_at").setValue(todo.getCreatedAt());
    }

    private void removeTodo(String title) {
        for (Todo todo : todoList) {
            if (todo.getTitle().equals(title)) {
                todoList.remove(todo);
                break;
            }
        }
        todoListDatabaseReference.child(title.replace(' ', '_')).setValue(null);
    }

    private void addNewItem(int todoIndex, Item item) {
        itemListDatabaseReference = todoListDatabaseReference.child(todoList.get(todoIndex).getTitle().replace(' ', '_')).child("item_list").getRef();
        itemListDatabaseReference.child(item.getTitle().replace(' ', '_')).child("is_done").setValue(item.isDone());
        itemListDatabaseReference.child(item.getTitle().replace(' ', '_')).child("created_at").setValue(item.getCreatedAt());
    }

    private void relocateItems(String title, List<Item> itemList) {
        itemListDatabaseReference = todoListDatabaseReference.child(title.replace(' ', '_')).child("item_list").getRef();
        for (Item item : itemList) {
            itemListDatabaseReference.child(item.getTitle().replace(' ', '_')).child("is_done").setValue(item.isDone());
            itemListDatabaseReference.child(item.getTitle().replace(' ', '_')).child("created_at").setValue(item.getCreatedAt());
        }
    }

    private void removeItem(int todoIndex, String title) {
        for (Item item : todoList.get(todoIndex).getItemList()) {
            if (item.getTitle().equals(title)) {
                todoList.get(todoIndex).getItemList().remove(item);
                break;
            }
        }
        itemListDatabaseReference = todoListDatabaseReference.child(todoList.get(todoIndex).getTitle().replace(' ', '_')).child("item_list").getRef();
        itemListDatabaseReference.child(title.replace(' ', '_')).setValue(null);
    }

    private void setItemDone(int todoIndex, int itemIndex, boolean done) {
        Item item = todoList.get(todoIndex).getItem(itemIndex);
        if (done) {
            item.markAsDone();
        } else {
            item.markAsNotDone();
        }
        addNewItem(todoIndex, item);
    }
}
