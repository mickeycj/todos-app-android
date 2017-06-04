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

import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.Todo;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.adapter.TodoListAdapter;
import ssd.project.mickeycj.todosapp.view.dialog.OptionsDialog;

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
        todoListAdapter = new TodoListAdapter(todoList, onTodoClickListener);

        initViewHolders();

        updateTodoList();
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

    private TodoListAdapter.OnViewHolderClickListener onTodoClickListener = new TodoListAdapter.OnViewHolderClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            final OptionsDialog optionsDialog = new OptionsDialog(TodoListActivity.this);
            View.OnClickListener onShowItemListClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            };

            View.OnClickListener onEditTodoClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TodoListActivity.this, EditTodoActivity.class);
                    intent.putExtra("todoIndex", position);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            };

            View.OnClickListener onDeleteTodoClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Repository.removeTodoFromCurrentTodoList(position);
                    updateTodoList();
                    optionsDialog.dismiss();
                }
            };
            optionsDialog
                    .setOptionsTitle(String.format(getString(R.string.options_title), "TODO"))
                    .setFirstOptionButton(getString(R.string.option_show_items), onShowItemListClickListener)
                    .setSecondOptionButton(getString(R.string.option_edit_todo), onEditTodoClickListener)
                    .setThirdOptionButton(getString(R.string.option_delete_todo), onDeleteTodoClickListener)
                    .show();
        }
    };

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onNewTodoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(NewTodoActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(new Intent(TodoListActivity.this, cls));
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void updateTodoList() {
        if (!todoList.isEmpty()) {
            todoList.clear();
        }
        todoList.addAll(Repository.getCurrentTodoList());
        todoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() { startActivity(MainActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
