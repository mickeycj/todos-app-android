package ssd.project.mickeycj.todosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String username, email, password, passwordConfirmation;

    private EditText usernameEditText, emailEditText, passwordEditText, passwordConfirmationEditText;
    private Button signUpButton, cancelButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        initViewHolders();
    }

    private void initViewHolders() {
        usernameEditText = (EditText) findViewById(R.id.edittext_sign_up_username);
        emailEditText = (EditText) findViewById(R.id.edittext_sign_up_email);
        passwordEditText = (EditText) findViewById(R.id.edittext_sign_up_password);
        passwordConfirmationEditText = (EditText) findViewById(R.id.edittext_sign_up_password_confirmation);
        clearEditTexts();

        signUpButton = (Button) findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(onSignUpClickListener);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(onCancelClickListener);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up...");
    }

    private View.OnClickListener onSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog.show();
            username = getUsernameFromEditText();
            email = getEmailFromEditText();
            password = getPasswordFromEditText();
            passwordConfirmation = getPasswordConfirmationFromEditText();
            if (isValidSignUp()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startSignInActivity();
                        } else {
                            clearEditTexts();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startSignInActivity();
        }
    };

    private boolean isValidSignUp() {
        return !username.equals("") && !email.equals("") && !password.equals("") && !passwordConfirmation.equals("") && password.equals(passwordConfirmation);
    }

    private void startSignInActivity() {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private String getUsernameFromEditText() { return usernameEditText.getText().toString().trim(); }

    private String getEmailFromEditText() { return emailEditText.getText().toString().trim(); }

    private String getPasswordFromEditText() { return passwordEditText.getText().toString().trim(); }

    private String getPasswordConfirmationFromEditText() { return passwordConfirmationEditText.getText().toString().trim(); }

    private void clearEditTexts() {
        usernameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        passwordConfirmationEditText.setText("");
    }
}
