package ssd.project.mickeycj.todosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ssd.project.mickeycj.todosapp.models.User;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private long delayTime, time = 1500L;

    private TextView usernameTextView;
    private Button helpButton, viewTodosButtons, viewProfileButton, signOutButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                User.signOut();
                progressDialog.dismiss();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        };

        initViewHolders();
    }

    private void initViewHolders() {
        usernameTextView = (TextView) findViewById(R.id.textview_username);

        helpButton = (Button) findViewById(R.id.button_help);
        helpButton.setOnClickListener(onHelpClickListener);
        viewTodosButtons = (Button) findViewById(R.id.button_view_todos);
        viewTodosButtons.setOnClickListener(onViewTodosClickListener);
        viewProfileButton = (Button) findViewById(R.id.button_view_profile);
        viewProfileButton.setOnClickListener(onViewProfileClickListener);
        signOutButton = (Button) findViewById(R.id.button_sign_out);
        signOutButton.setOnClickListener(onSignOutClickListener);

        progressDialog = new ProgressDialog(this);
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

        }
    };

    private View.OnClickListener onViewProfileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onSignOutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signOut();
        }
    };

    private void signOut() {
        progressDialog.show();
        handler.postDelayed(runnable, delayTime = time);
        time = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delayTime - (System.currentTimeMillis() - time);
    }

    @Override
    public void onBackPressed() { signOut(); }
}
