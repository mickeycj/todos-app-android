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

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String email, password;

    private EditText emailEditText, passwordEditText;
    private Button signInButton, signUpButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        initViewHolders();
    }

    private void initViewHolders() {
        emailEditText = (EditText) findViewById(R.id.edittext_sign_in_email);
        passwordEditText = (EditText) findViewById(R.id.edittext_sign_in_password);
        clearEditTexts();

        signInButton = (Button) findViewById(R.id.button_sign_in);
        signInButton.setOnClickListener(onSignInClickListener);
        signUpButton = (Button) findViewById(R.id.button_sign_up_now);
        signUpButton.setOnClickListener(onSignUpClickListener);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
    }

    private View.OnClickListener onSignInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog.show();
            email = getEmailFromEditText();
            password = getPasswordFromEditText();
            if (isValidSignIn()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            clearEditTexts();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
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

    private boolean isValidSignIn() {
        return !email.equals("") && !password.equals("");
    }

    private String getEmailFromEditText() { return emailEditText.getText().toString().trim(); }

    private String getPasswordFromEditText() { return passwordEditText.getText().toString().trim(); }

    private void clearEditTexts() {
        emailEditText.setText("");
        passwordEditText.setText("");
    }
}
