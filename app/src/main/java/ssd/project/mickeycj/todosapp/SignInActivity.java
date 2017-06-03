package ssd.project.mickeycj.todosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViewHolders();
    }

    private void initViewHolders() {
        emailEditText = (EditText) findViewById(R.id.edittext_sign_in_email);
        passwordEditText = (EditText) findViewById(R.id.edittext_sign_in_password);

        signInButton = (Button) findViewById(R.id.button_sign_in);
        signInButton.setOnClickListener(onSignInClickListener);
        signUpButton = (Button) findViewById(R.id.button_sign_up_now);
        signUpButton.setOnClickListener(onSignUpClickListener);
    }

    private View.OnClickListener onSignInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Authenticate the user

        }
    };

    private View.OnClickListener onSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };
}
