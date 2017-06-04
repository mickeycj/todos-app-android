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

import ssd.project.mickeycj.todosapp.model.User;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;

public class SignUpActivity extends AppCompatActivity {

    private String username, email, password, passwordConfirmation;

    private EditText usernameEditText, emailEditText, passwordEditText, passwordConfirmationEditText;
    private Button signUpButton, cancelButton;
    private ProgressDialog progressDialog;

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
        passwordConfirmationEditText.setOnEditorActionListener(onActionDoneListener);
        clearEditTexts();

        signUpButton = (Button) findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(onSignUpClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(onCancelClickListener);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Signing up...");
    }

    private TextView.OnEditorActionListener onActionDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signUp();
            }
            return false;
        }
    };

    private View.OnClickListener onSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signUp();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void signUp() {
        username = getUsernameFromEditText();
        email = getEmailFromEditText();
        password = getPasswordFromEditText();
        passwordConfirmation = getPasswordConfirmationFromEditText();
        if (isValidSignUp()) {
            User.signUp(username, email, password, SignUpActivity.this);
        }
    }

    private boolean isValidSignUp() {
        boolean valid = true;
        String alertTitle = "";
        StringBuilder alertContent = new StringBuilder();
        if (username.equals("")) {
            if (alertTitle.length() == 0) {
                alertTitle = getString(R.string.uncompleted_form);
            }
            if (alertContent.length() == 0) {
                alertContent.append("\n");
            }
            alertContent.append(getString(R.string.missing_username));
            valid = false;
        }
        if (email.equals("")) {
            if (alertTitle.length() == 0) {
                alertTitle = getString(R.string.uncompleted_form);
            }
            if (alertContent.length() != 0) {
                alertContent.append("\n");
            }
            alertContent.append(getString(R.string.missing_email));
            valid = false;
        }
        if (password.equals("")) {
            if (alertTitle.length() == 0) {
                alertTitle = getString(R.string.uncompleted_form);
            }
            if (alertContent.length() != 0) {
                alertContent.append("\n");
            }
            alertContent.append(getString(R.string.missing_password));
            valid = false;
        }
        if (passwordConfirmation.equals("")) {
            if (alertTitle.length() == 0) {
                alertTitle = getString(R.string.uncompleted_form);
            }
            if (alertContent.length() != 0) {
                alertContent.append("\n");
            }
            alertContent.append(getString(R.string.missing_password_confirmation));
            valid = false;
        }
        if (!password.equals("") && !passwordConfirmation.equals("") && !password.equals(passwordConfirmation)) {
            if (alertTitle.length() == 0) {
                alertTitle = getString(R.string.wrong_confirmation);
            }
            alertContent.append(getString(R.string.wrong_confirmation_detail));
            valid = false;
        }
        if (alertTitle.length() != 0) {
            new AlertDialog(SignUpActivity.this)
                    .setAlertTitle(alertTitle)
                    .setAlertContent(alertContent.toString())
                    .show();
        }
        return valid;
    }

    private String getUsernameFromEditText() { return usernameEditText.getText().toString().trim(); }

    private String getEmailFromEditText() { return emailEditText.getText().toString().trim(); }

    private String getPasswordFromEditText() { return passwordEditText.getText().toString().trim(); }

    private String getPasswordConfirmationFromEditText() { return passwordConfirmationEditText.getText().toString().trim(); }

    public void startSignInActivity() {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void clearEditTexts() {
        usernameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        passwordConfirmationEditText.setText("");
    }

    public void showProgressDialog() { progressDialog.show(); }

    public void dismissProgressDialog() { progressDialog.dismiss(); }

    @Override
    public void onBackPressed() { startSignInActivity(); }
}
