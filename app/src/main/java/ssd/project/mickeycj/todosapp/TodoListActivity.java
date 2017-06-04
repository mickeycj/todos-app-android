package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ssd.project.mickeycj.todosapp.model.Todo;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.adapter.TodoListAdapter;

public class TodoListActivity extends AppCompatActivity {

    private List<Todo> todoList;

    private TextView usernameAppBarTextView, ownerTextView;
    private Button helpButton, newTodoButton, backButton;

    private RecyclerView todoListRecyclerView;
    private RecyclerView.Adapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        todoList = new ArrayList<>();
        todoListAdapter = new TodoListAdapter(todoList);

        initViewHolders();

        // Mock-up list
        if (!todoList.isEmpty()) {
            todoList.clear();
        }
        todoList.add(new Todo("Todo #1", false));
        todoList.add(new Todo("Todo #2", true));
        todoListAdapter.notifyDataSetChanged();
    }

    private void initViewHolders() {
        String username = User.getCurrentUser().getUsername();
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_todo_list);
        usernameAppBarTextView.setText(username);
        ownerTextView = (TextView) findViewById(R.id.textview_todo_list_owner);
        ownerTextView.setText(String.format(getString(R.string.todo_list_owner), username));

        helpButton = (Button) findViewById(R.id.button_help_todo_list);
        helpButton.setOnClickListener(onHelpClickListener);
        newTodoButton = (Button) findViewById(R.id.button_new_todo);
        newTodoButton.setOnClickListener(onNewTodoClickListener);
        backButton = (Button) findViewById(R.id.button_back_from_todo_list);
        backButton.setOnClickListener(onBackClickListener);

        todoListRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_todo_list);
        todoListRecyclerView.setLayoutManager(new LinearLayoutManager(TodoListActivity.this, LinearLayoutManager.VERTICAL, false));
        todoListRecyclerView.setHasFixedSize(true);
        todoListRecyclerView.setAdapter(todoListAdapter);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onNewTodoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls) {
        startActivity(new Intent(TodoListActivity.this, cls));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() { startActivity(MainActivity.class); }
}