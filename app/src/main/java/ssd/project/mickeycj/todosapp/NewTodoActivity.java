package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.Todo;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;

public class NewTodoActivity extends AppCompatActivity {

    private TextView usernameAppBarTextView, ownerTextView;
    private EditText todoTitleEditText;
    private CheckBox todoImportanceCheckBox;
    private Button helpButton, confirmButton, cancelButton;

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
        todoImportanceCheckBox = (CheckBox) findViewById(R.id.checkbox_new_todo_importance);
        clearForm();

        helpButton = (Button) findViewById(R.id.button_help_new_todo);
        helpButton.setOnClickListener(onHelpClickListener);
        confirmButton = (Button) findViewById(R.id.button_confirm_new_todo);
        confirmButton.setOnClickListener(onConfirmClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel_new_todo);
        cancelButton.setOnClickListener(onCancelClickListener);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onConfirmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
                            .show();
                }
            }
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

    private String getTodoTitleFromEditText() { return todoTitleEditText.getText().toString().trim(); }

    private boolean getTodoImportanceFromCheckBox() { return todoImportanceCheckBox.isChecked(); }

    private void clearForm() {
        todoTitleEditText.setText("");
        todoImportanceCheckBox.setChecked(false);
    }

    @Override
    public void onBackPressed() { startActivity(TodoListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
