package ssd.project.mickeycj.todosapp.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import ssd.project.mickeycj.todosapp.SignInActivity;
import ssd.project.mickeycj.todosapp.SignUpActivity;

/**
 * Created by user on 3/6/60.
 */

public class User {

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private static String username, email;
    private static Date joinedAt;

    // TODO Store todo list


    public static boolean isSignedIn() { return firebaseAuth.getCurrentUser() != null; }

    public static void signIn(final String email, String password, final SignInActivity signInActivity) {
        signInActivity.showProgressDialog();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(signInActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // TODO Set user's details

                    // Then start main activity
                    signInActivity.startMainActivity();
                } else {
                    signInActivity.clearEditTexts();
                }
                signInActivity.dismissProgressDialog();
            }
        });
    }

    public static void signUp(String email, String password, final SignUpActivity signUpActivity) {
        signUpActivity.showProgressDialog();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signUpActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signUpActivity.startSignInActivity();
                } else {
                    signUpActivity.clearEditTexts();
                }
                signUpActivity.dismissProgressDialog();
            }
        });
    }

    public static void signOut() { firebaseAuth.signOut(); }

    public static String getUsername() { return username; }

    public static String getEmail() { return email; }

    public static Date getJoinedAt() { return joinedAt; }

    private static void setUsername(String username) { User.username = username; }

    private static void setEmail(String email) { User.email = email; }

    private static void setJoinedAt(Date joinedAt) { User.joinedAt = joinedAt; }
}
