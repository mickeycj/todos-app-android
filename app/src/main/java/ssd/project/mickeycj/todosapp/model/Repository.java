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

    public static Todo getTodoFromCurrentTodoList(int index) { return currentRepository.getTodo(index); }

    public static void addNewTodoToCurrentTodoList(Todo todo) { currentRepository.addNewTodo(todo); }

    public static void removeTodoFromCurrentTodoList(String title) { currentRepository.removeTodo(title); }

    public static void removeTodoFromCurrentTodoList(int index) { removeTodoFromCurrentTodoList(currentRepository.getTodo(index).getTitle()); }

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
                String title = dataSnapshot.getKey().replace('_', ' ');
                boolean importance = dataSnapshot.child("importance").getValue(Boolean.class);
                Date createdAt = dataSnapshot.child("created_at").getValue(Date.class);
                Todo todo = new Todo(title, importance, createdAt);
                final List<Item> itemList = new ArrayList<>();
                itemListDatabaseReference = dataSnapshot.child("item_list").getRef();
                itemListDatabaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String title = dataSnapshot.getKey().replace('_', ' ');
                        boolean done = dataSnapshot.child("is_done").getValue(Boolean.class);
                        Date createdAt = dataSnapshot.child("created_at").getValue(Date.class);
                        itemList.add(new Item(title, done, createdAt));
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
                todo.setItemList(itemList);
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

    private Todo getTodo(int index) { return todoList.get(index); }

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
}
