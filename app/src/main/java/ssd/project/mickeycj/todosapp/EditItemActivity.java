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

import java.util.Date;

import ssd.project.mickeycj.todosapp.model.Item;
import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;

public class EditItemActivity extends AppCompatActivity {

    private int todoIndex;
    private String itemTitle;
    private boolean itemDone;
    private Date itemCreatedAt;

    private TextView usernameAppBarTextView, ownerTextView;
    private EditText itemTitleEditText;
    private Button helpButton, editButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        todoIndex = getIntent().getIntExtra("todoIndex", 0);
        Item item = Repository.getCurrentItemFromCurrentTodo(todoIndex, getIntent().getIntExtra("itemIndex", 0));
        itemTitle = item.getTitle();
        itemDone = item.isDone();
        itemCreatedAt = item.getCreatedAt();

        initViewHolders();
    }

    private void initViewHolders() {
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_edit_item);
        usernameAppBarTextView.setText(User.getCurrentUser().getUsername());
        ownerTextView = (TextView) findViewById(R.id.textview_edit_item_owner);
        ownerTextView.setText(String.format(getString(R.string.item_list_owner), Repository.getTodoFromCurrentTodoList(todoIndex).getTitle()));

        itemTitleEditText = (EditText) findViewById(R.id.edittext_edit_item_title);
        itemTitleEditText.setOnEditorActionListener(onActionDoneListener);
        itemTitleEditText.setText("");

        helpButton = (Button) findViewById(R.id.button_help_edit_item);
        helpButton.setOnClickListener(onHelpClickListener);
        editButton = (Button) findViewById(R.id.button_confirm_edit_item);
        editButton.setOnClickListener(onEditClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel_edit_item);
        cancelButton.setOnClickListener(onCancelClickListener);
    }

    private TextView.OnEditorActionListener onActionDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editItem();
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
            editItem();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(new Intent(EditItemActivity.this, cls));
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void editItem() {
        String title = getItemTitleFromEditText();
        if (!title.equals("") && !title.contains(".") && !title.contains("#") && !title.contains("$") && !title.contains("[") && !title.contains("]")) {
            Repository.addNewItemToCurrentItemListInCurrentTodo(todoIndex, new Item(title, itemDone, itemCreatedAt));
            onBackPressed();
        } else {
            if (title.equals("")) {
                new AlertDialog(EditItemActivity.this)
                        .setAlertTitle(getString(R.string.uncompleted_form))
                        .show();
            } else {
                new AlertDialog(EditItemActivity.this)
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
