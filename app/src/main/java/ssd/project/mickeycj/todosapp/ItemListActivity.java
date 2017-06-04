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

import ssd.project.mickeycj.todosapp.model.Item;
import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.OnViewHolderClickListener;
import ssd.project.mickeycj.todosapp.view.adapter.ItemListAdapter;
import ssd.project.mickeycj.todosapp.view.dialog.OptionsDialog;

public class ItemListActivity extends AppCompatActivity {

    private int todoIndex;
    private List<Item> itemList;

    private TextView usernameAppBarTextView, ownerTextView;
    private Button helpButton, newItemButton, backButton;

    private RecyclerView itemListRecyclerView;
    private RecyclerView.Adapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        todoIndex = getIntent().getIntExtra("todoIndex", 0);
        itemList = new ArrayList<>();
        itemListAdapter = new ItemListAdapter(itemList, onItemClickListener);

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

        itemListRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_item_list);
        itemListRecyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this, LinearLayoutManager.VERTICAL, false));
        itemListRecyclerView.setHasFixedSize(true);
        itemListRecyclerView.setAdapter(itemListAdapter);
    }

    private OnViewHolderClickListener onItemClickListener = new OnViewHolderClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            final OptionsDialog optionsDialog = new OptionsDialog(ItemListActivity.this);
            View.OnClickListener onMarkItemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateItemList();
                    optionsDialog.dismiss();
                }
            };
            View.OnClickListener onEditItemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            };
            View.OnClickListener onDeleteItemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateItemList();
                    optionsDialog.dismiss();
                }
            };
            optionsDialog
                    .setOptionsTitle(String.format(getString(R.string.options_title), "ITEM"))
                    .setFirstOptionButton(getString(R.string.option_mark_item), onMarkItemClickListener)
                    .setSecondOptionButton(getString(R.string.option_edit_item), onEditItemClickListener)
                    .setThirdOptionButton(getString(R.string.option_delete_item), onDeleteItemClickListener)
                    .show();
        }
    };

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
        if (!itemList.isEmpty()) {
            itemList.clear();
        }

        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() { startActivity(MainActivity.class, R.anim.slide_in_left, R.anim.slide_out_right); }
}
