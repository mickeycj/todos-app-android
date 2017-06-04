package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ssd.project.mickeycj.todosapp.model.Item;
import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;
import ssd.project.mickeycj.todosapp.view.dialog.HelpDialog;

public class NewItemActivity extends AppCompatActivity {

    private int todoIndex;

    private TextView usernameAppBarTextView, ownerTextView;
    private EditText itemTitleEditText;
    private Button confirmButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        todoIndex = getIntent().getIntExtra("todoIndex", 0);

        initViewHolders();
    }

    private void initViewHolders() {
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_new_item);
        usernameAppBarTextView.setText(User.getCurrentUser().getUsername());
        ownerTextView = (TextView) findViewById(R.id.textview_new_item_owner);
        ownerTextView.setText(String.format(getString(R.string.item_list_owner), Repository.getTodoFromCurrentTodoList(todoIndex).getTitle()));

        itemTitleEditText = (EditText) findViewById(R.id.edittext_new_item_title);
        itemTitleEditText.setOnEditorActionListener(onActionDoneListener);
        itemTitleEditText.setText("");

        confirmButton = (Button) findViewById(R.id.button_confirm_new_item);
        confirmButton.setOnClickListener(onConfirmClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel_new_item);
        cancelButton.setOnClickListener(onCancelClickListener);
    }

    private TextView.OnEditorActionListener onActionDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addNewItem();
            }
            return false;
        }
    };

    public void onHelpClick(View view) {
        new HelpDialog(NewItemActivity.this)
                .setHelpTitle(getString(R.string.help_title_new_item))
                .setFirstHelpContent(getString(R.string.help_detail_new_item))
                .show();
    }

    private View.OnClickListener onConfirmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewItem();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        Intent intent = new Intent(NewItemActivity.this, cls);
        intent.putExtra("todoIndex", todoIndex);
        startActivity(intent);
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void addNewItem() {
        String title = getItemTitleFromEditText();
        if (!title.equals("") && !title.contains(".") && !title.contains("#") && !title.contains("$") && !title.contains("[") && !title.contains("]")) {
            Repository.addNewItemToCurrentItemListInCurrentTodo(todoIndex, new Item(title));
            onBackPressed();
        } else {
            if (title.equals("")) {
                new AlertDialog(NewItemActivity.this)
                        .setAlertTitle(getString(R.string.uncompleted_form))
                        .show();
            } else {
                new AlertDialog(NewItemActivity.this)
                        .setAlertTitle(getString(R.string.invalid_characters))
                        .setAlertContent(getString(R.string.invalid_characters_details))
                        .show();
            }
        }
    }

    private String getItemTitleFromEditText() { return itemTitleEditText.getText().toString().trim(); }

    @Override
    public void onBackPressed() { startActivity(ItemListActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
