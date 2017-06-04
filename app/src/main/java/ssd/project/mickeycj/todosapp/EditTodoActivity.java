package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.Todo;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;

public class EditTodoActivity extends AppCompatActivity {

    private int todoIndex;
    private String todoTitle;
    private boolean todoImportance;
    private Date todoCreatedAt;

    private TextView usernameAppBarTextView, ownerTextView;
    private EditText todoTitleEditText;
    private CheckBox todoImportanceCheckBox;
    private Button helpButton, editButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        todoIndex = getIntent().getIntExtra("todoIndex", 0);
        Todo todo = Repository.getTodoFromCurrentTodoList(todoIndex);
        todoTitle = todo.getTitle();
        todoImportance = todo.isImportant();
        todoCreatedAt = todo.getCreatedAt();

        initViewHolders();
    }

    private void initViewHolders() {
        String username = User.getCurrentUser().getUsername();
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_edit_todo);
        usernameAppBarTextView.setText(username);
        ownerTextView = (TextView) findViewById(R.id.textview_edit_todo_owner);
        ownerTextView.setText(String.format(getString(R.string.todo_list_owner), username));

        todoTitleEditText = (EditText) findViewById(R.id.edittext_edit_todo_title);
        todoTitleEditText.setOnEditorActionListener(onActionDoneListener);
        todoImportanceCheckBox = (CheckBox) findViewById(R.id.checkbox_edit_todo_importance);
        resetForm();

        helpButton = (Button) findViewById(R.id.button_help_edit_todo);
        helpButton.setOnClickListener(onHelpClickListener);
        editButton = (Button) findViewById(R.id.button_confirm_edit_todo);
        editButton.setOnClickListener(onEditClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel_edit_todo);
        cancelButton.setOnClickListener(onCancelClickListener);
    }

    private TextView.OnEditorActionListener onActionDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editTodo();
            }
            return false;
        }
    };

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editTodo();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(new Intent(EditTodoActivity.this, cls));
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void editTodo() {
        String title = getTodoTitleFromEditText();
        boolean importance = getTodoImportanceFromCheckBox();
        if (!title.equals("") && !title.contains(".") && !title.contains("#") && !title.contains("$") && !title.contains("[") && !title.contains("]")) {
            if (!todoTitle.equals(title) || todoImportance != importance) {
                if (!todoTitle.equals(title)) {
                    Repository.relocateItemsInCurrentItemListFromCurrentTodo(title, Repository.getCurrentItemListFromCurrentTodo(todoIndex));
                }
                Repository.removeTodoFromCurrentTodoList(todoTitle);
            }
            Repository.addNewTodoToCurrentTodoList(new Todo(title, importance, todoCreatedAt));
            onBackPressed();
        } else {
            if (title.equals("")) {
                new AlertDialog(EditTodoActivity.this)
                        .setAlertTitle(getString(R.string.uncompleted_form))
                        .show();
            } else {
                new AlertDialog(EditTodoActivity.this)
                        .setAlertTitle(getString(R.string.invalid_characters))
                        .setAlertContent(getString(R.string.invalid_characters_details))
                        .show();
            }
            resetForm();
        }
    }

    private String getTodoTitleFromEditText() { return todoTitleEditText.getText().toString().trim(); }

    private boolean getTodoImportanceFromCheckBox() { return todoImportanceCheckBox.isChecked(); }

    private void resetForm() {
        todoTitleEditText.setText(todoTitle);
        todoImportanceCheckBox.setChecked(todoImportance);
    }

    @Override
    public void onBackPressed() { startActivity(TodoListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
