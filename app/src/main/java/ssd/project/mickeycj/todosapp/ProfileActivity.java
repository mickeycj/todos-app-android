package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ssd.project.mickeycj.todosapp.models.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameAppBarTextView, usernameDetailTextView, emailDetailTextView, joinedAtDetailTextView;
    private Button helpButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViewHolders();
    }

    private void initViewHolders() {
        User currentUser = User.getCurrentUser();
        usernameAppBarTextView = (TextView) findViewById(R.id.textview_username_profile);
        usernameAppBarTextView.setText(currentUser.getUsername());
        usernameDetailTextView = (TextView) findViewById(R.id.textview_detail_username);
        usernameDetailTextView.setText(currentUser.getUsername());
        emailDetailTextView = (TextView) findViewById(R.id.textview_detail_email);
        emailDetailTextView.setText(currentUser.getEmail());
        joinedAtDetailTextView = (TextView) findViewById(R.id.textview_detail_joined_at);
        joinedAtDetailTextView.setText(new SimpleDateFormat("MMMM dd, yyyy").format(currentUser.getJoinedAt()));

        helpButton = (Button) findViewById(R.id.button_help_profile);
        helpButton.setOnClickListener(onHelpClickListener);
        backButton = (Button) findViewById(R.id.button_back_from_profile);
        backButton.setOnClickListener(onBackClickListener);
    }

    private View.OnClickListener onHelpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
