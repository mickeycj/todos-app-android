package ssd.project.mickeycj.todosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ssd.project.mickeycj.todosapp.model.Repository;
import ssd.project.mickeycj.todosapp.model.User;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private long delayTime, time = 1500L;

    private TextView usernameAppBarTextView;
    private Button helpButton, viewTodosButtons, viewProfileButton, signOutButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String email = User.getCurrentUser().getEmail();
        Repository.getCurrentRepository(email.substring(0, email.indexOf('.')));

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                User.signOut();
                progressDialog.dismiss();
                startActivity(SignInActivity.class, R.anim.slide_in_left, R.anim.slide_out_right);
            }
        };

        initViewHolders();
    }

    private void initViewHolders() {
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_main);
        usernameAppBarTextView.setText(User.getCurrentUser().getUsername());

        helpButton = (Button) findViewById(R.id.button_help_main);
        helpButton.setOnClickListener(onHelpClickListener);
        viewTodosButtons = (Button) findViewById(R.id.button_view_todos);
        viewTodosButtons.setOnClickListener(onViewTodosClickListener);
        viewProfileButton = (Button) findViewById(R.id.button_view_profile);
        viewProfileButton.setOnClickListener(onViewProfileClickListener);
        signOutButton = (Button) findViewById(R.id.button_sign_out);
        signOutButton.setOnClickListener(onSignOutClickListener);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Signing out...");
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onViewTodosClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(TodoListActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    private View.OnClickListener onViewProfileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(ProfileActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    private View.OnClickListener onSignOutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(new Intent(MainActivity.this, cls));
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delayTime - (System.currentTimeMillis() - time);
    }

    @Override
    public void onBackPressed() {
        progressDialog.show();
        handler.postDelayed(runnable, delayTime = time);
        time = System.currentTimeMillis();
    }
}
