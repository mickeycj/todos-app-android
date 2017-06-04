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

import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.Todo;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;
import ssd.project.mickeycj.todosapp.view.dialog.HelpDialog;

public class NewTodoActivity extends AppCompatActivity {

    private TextView usernameAppBarTextView, ownerTextView;
    private EditText todoTitleEditText;
    private CheckBox todoImportanceCheckBox;
    private Button confirmButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);

        initViewHolders();
    }

    private void initViewHolders() {
        String username = User.getCurrentUser().getUsername();
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_new_todo);
        usernameAppBarTextView.setText(username);
        ownerTextView = (TextView) findViewById(R.id.textview_new_todo_owner);
        ownerTextView.setText(String.format(getString(R.string.todo_list_owner), username));

        todoTitleEditText = (EditText) findViewById(R.id.edittext_new_todo_title);
        todoTitleEditText.setOnEditorActionListener(onActionDoneListener);
        todoImportanceCheckBox = (CheckBox) findViewById(R.id.checkbox_new_todo_importance);
        clearForm();

        confirmButton = (Button) findViewById(R.id.button_confirm_new_todo);
        confirmButton.setOnClickListener(onConfirmClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel_new_todo);
        cancelButton.setOnClickListener(onCancelClickListener);
    }

    private TextView.OnEditorActionListener onActionDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addNewTodo();
            }
            return false;
        }
    };

    public void onHelpClick(View view) {
        new HelpDialog(NewTodoActivity.this)
                .setHelpTitle(getString(R.string.help_title_new_todo))
                .setFirstHelpContent(getString(R.string.help_detail_new_todo))
                .show();
    }

    private View.OnClickListener onConfirmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewTodo();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(new Intent(NewTodoActivity.this, cls));
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void addNewTodo() {
        String title = getTodoTitleFromEditText();
        boolean importance = getTodoImportanceFromCheckBox();
        if (!title.equals("") && !title.contains(".") && !title.contains("#") && !title.contains("$") && !title.contains("[") && !title.contains("]")) {
            Repository.addNewTodoToCurrentTodoList(new Todo(title, importance));
            onBackPressed();
        } else {
            if (title.equals("")) {
                new AlertDialog(NewTodoActivity.this)
                        .setAlertTitle(getString(R.string.uncompleted_form))
                        .show();
            } else {
                new AlertDialog(NewTodoActivity.this)
                        .setAlertTitle(getString(R.string.invalid_characters))
                        .setAlertContent(getString(R.string.invalid_characters_details))
                        .show();
            }
        }
    }

    private String getTodoTitleFromEditText() { return todoTitleEditText.getText().toString().trim(); }

    private boolean getTodoImportanceFromCheckBox() { return todoImportanceCheckBox.isChecked(); }

    private void clearForm() {
        todoTitleEditText.setText("");
        todoImportanceCheckBox.setChecked(false);
    }

    @Override
    public void onBackPressed() { startActivity(TodoListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
