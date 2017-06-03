package ssd.project.mickeycj.todosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ssd.project.mickeycj.todosapp.models.User;

public class SignInActivity extends AppCompatActivity implements Observer {

    private String email, password;

    private EditText emailEditText, passwordEditText;
    private Button signInButton, signUpButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        User.getCurrentUser().addObserver(this);

        initViewHolders();
    }

    private void initViewHolders() {
        emailEditText = (EditText) findViewById(R.id.edittext_sign_in_email);
        passwordEditText = (EditText) findViewById(R.id.edittext_sign_in_password);
        passwordEditText.setOnEditorActionListener(onActionDoneListener);
        clearEditTexts();

        signInButton = (Button) findViewById(R.id.button_sign_in);
        signInButton.setOnClickListener(onSignInClickListener);
        signUpButton = (Button) findViewById(R.id.button_sign_up_now);
        signUpButton.setOnClickListener(onSignUpClickListener);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
    }

    private TextView.OnEditorActionListener onActionDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signIn();
            }
            return false;
        }
    };

    private View.OnClickListener onSignInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn();
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

    private void signIn() {
        email = getEmailFromEditText();
        password = getPasswordFromEditText();
        if (isValidSignIn()) {
            User.signIn(email, password, SignInActivity.this);
        }
    }

    private boolean isValidSignIn() {
        return !email.equals("") && !password.equals("");
    }

    private String getEmailFromEditText() { return emailEditText.getText().toString().trim(); }

    private String getPasswordFromEditText() { return passwordEditText.getText().toString().trim(); }

    public void clearEditTexts() {
        emailEditText.setText("");
        passwordEditText.setText("");
    }

    public void showProgressDialog() { progressDialog.show(); }

    public void dismissProgressDialog() { progressDialog.dismiss(); }

    @Override
    public void update(Observable o, Object arg) {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
