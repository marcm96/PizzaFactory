package com.pfac.marcm.pizzafactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button signInButton;

    private FirebaseAuth mAuth;
    private DatabaseReference usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usersDB = FirebaseDatabase.getInstance().getReference("users");
        mAuth =  FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.usernameEditText);
        mPasswordField = findViewById(R.id.passwordEditText);

        signInButton = findViewById(R.id.signinButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



    }

    private void signIn() {
        String username = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = "";
                    if (firebaseUser != null) {
                        userId = firebaseUser.getUid();
                    }

                    final String finalUserId = userId;

                    usersDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String role = null;
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                if (Objects.equals(ds.getKey(), finalUserId)){
                                    String valueRole = ds.getValue().toString();
                                    String roleValue = valueRole.split("=")[1];
                                    role = roleValue.substring(0, roleValue.length() - 1);
                                }
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("role", role);
                            editor.putString("userId", finalUserId);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}