package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.HelpDialog;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameAppBarTextView, usernameDetailTextView, emailDetailTextView, joinedAtDetailTextView;
    private Button backButton;

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

        backButton = (Button) findViewById(R.id.button_back_from_profile);
        backButton.setOnClickListener(onBackClickListener);
    }

    public void onHelpClick(View view) {
        new HelpDialog(ProfileActivity.this)
                .setHelpTitle(getString(R.string.help_title_profile))
                .setFirstHelpContent(getString(R.string.help_detail_profile))
                .show();
    }

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
