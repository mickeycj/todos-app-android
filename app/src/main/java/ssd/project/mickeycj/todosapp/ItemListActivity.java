package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ssd.project.mickeycj.todosapp.model.Item;
import ssd.project.mickeycj.todosapp.model.User;

public class ItemListActivity extends AppCompatActivity {

    private List<Item> itemList;

    private TextView usernameAppBarTextView, ownerTextView;
    private Button helpButton, newItemButton, backButton;

    private RecyclerView itemListRecyclerView;
    private RecyclerView.Adapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemList = new ArrayList<>();
        // itemListAdapter

        initViewHolders();

        updateItemList();
    }

    private void initViewHolders() {
        String username = User.getCurrentUser().getUsername();
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_todo_list);
        usernameAppBarTextView.setText(username);
        ownerTextView = (TextView) findViewById(R.id.textview_todo_list_owner);
        ownerTextView.setText(String.format(getString(R.string.todo_list_owner), username));

        helpButton = (Button) findViewById(R.id.button_help_todo_list);
        helpButton.setOnClickListener(onHelpClickListener);
        newItemButton = (Button) findViewById(R.id.button_new_todo);
        newItemButton.setOnClickListener(onNewItemClickListener);
        backButton = (Button) findViewById(R.id.button_back_from_todo_list);
        backButton.setOnClickListener(onBackClickListener);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onNewItemClickListener = new View.OnClickListener() {
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
        startActivity(new Intent(ItemListActivity.this, cls));
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void updateItemList() {

    }

    @Override
    public void onBackPressed() { startActivity(MainActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
