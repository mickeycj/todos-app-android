package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private Button helpButton, viewTodosButtons, viewProfileButton, signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    };
}
