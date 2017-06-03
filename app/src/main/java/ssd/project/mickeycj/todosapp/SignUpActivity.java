package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, passwordConfirmationEditText;
    private Button signUpButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViewHolders();
    }

    private void initViewHolders() {
        usernameEditText = (EditText) findViewById(R.id.edittext_sign_up_username);
        emailEditText = (EditText) findViewById(R.id.edittext_sign_up_email);
        passwordEditText = (EditText) findViewById(R.id.edittext_sign_up_password);
        passwordConfirmationEditText = (EditText) findViewById(R.id.edittext_sign_up_password_confirmation);

        signUpButton = (Button) findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(onSignUpClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(onCancelClickListener);
    }

    private View.OnClickListener onSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Sign-up the user

            // Then start sign-in activity
            startSignInActivity();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startSignInActivity();
        }
    };

    private void startSignInActivity() {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
