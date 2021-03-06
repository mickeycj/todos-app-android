package ssd.project.mickeycj.todosapp.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Observable;

import ssd.project.mickeycj.todosapp.R;
import ssd.project.mickeycj.todosapp.SignInActivity;
import ssd.project.mickeycj.todosapp.SignUpActivity;
import ssd.project.mickeycj.todosapp.view.dialog.AlertDialog;

/**
 * Created by user on 3/6/60.
 */

public class User extends Observable {

    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;

    private static User currentUser;

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User();
        }
        return currentUser;
    }

    public static boolean isSignedIn() {
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser() != null;
    }

    public static void signIn(final String email, String password, final SignInActivity signInActivity) {
        signInActivity.showProgressDialog();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(signInActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser.setEmail(email);
                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (dataSnapshot.getKey().equals(email.substring(0, email.indexOf('.')))) {
                                currentUser.setUsername(dataSnapshot.child("username").getValue(String.class));
                                currentUser.setJoinedAt(dataSnapshot.child("joined_at").getValue(Date.class));
                                currentUser.setChanged();
                                currentUser.notifyObservers();
                                signInActivity.dismissProgressDialog();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {}

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                } else {
                    signInActivity.dismissProgressDialog();
                    signInActivity.clearEditTexts();
                    new AlertDialog(signInActivity)
                            .setAlertTitle(signInActivity.getString(R.string.invalid_sign_in))
                            .setAlertContent(signInActivity.getString(R.string.invalid_sign_in_detail_1)
                                    + "\n" + signInActivity.getString(R.string.invalid_sign_in_detail_2))
                            .show();
                }
            }
        });
    }

    public static void signUp(final String username, final String email, String password, final SignUpActivity signUpActivity) {
        signUpActivity.showProgressDialog();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signUpActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    databaseReference.child(email.substring(0, email.indexOf('.'))).child("username").setValue(username);
                    databaseReference.child(email.substring(0, email.indexOf('.'))).child("joined_at").setValue(new Date());
                    signUpActivity.startSignInActivity();
                } else {
                    signUpActivity.clearEditTexts();
                    new AlertDialog(signUpActivity)
                            .setAlertTitle(signUpActivity.getString(R.string.invalid_sign_up))
                            .setAlertContent(signUpActivity.getString(R.string.invalid_sign_up_detail))
                            .show();
                }
                signUpActivity.dismissProgressDialog();
            }
        });
    }

    public static void signOut() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        if (currentUser != null) {
            currentUser.setUsername(null);
            currentUser.setEmail(null);
            currentUser.setJoinedAt(null);
        }
    }

    private String username, email;
    private Date joinedAt;

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public Date getJoinedAt() { return joinedAt; }

    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) { this.email = email; }

    public void setJoinedAt(Date joinedAt) { this.joinedAt = joinedAt; }
}
