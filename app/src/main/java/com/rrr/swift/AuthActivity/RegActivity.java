package com.rrr.swift.AuthActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rrr.swift.Main2Activity;
import com.rrr.swift.R;
import com.rrr.swift.temp.Main4Activity;

public class RegActivity extends AppCompatActivity {

    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText passwordText;
    EditText confirmPasswordText;
    Button regButton;
    Button backButton;
    FirebaseAuth dataRef;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        firstNameText = findViewById(R.id.firstname_reg);
        lastNameText = findViewById(R.id.lastname_reg);
        emailText = findViewById(R.id.email_reg);
        passwordText = findViewById(R.id.password_reg);
        confirmPasswordText = findViewById(R.id.password2_reg);
        regButton = findViewById(R.id.btn_submit);
        backButton = findViewById(R.id.btn_return);

        dataRef = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String firstName = firstNameText.getText().toString().trim();
                String lastName = lastNameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirmPassword = confirmPasswordText.getText().toString().trim();

                if(TextUtils.isEmpty(firstName) || firstName.length() < 3 || firstName.length() > 30){
                    Toast.makeText(getApplicationContext(), "Enter First Name",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lastName) || lastName.length() < 3 || lastName.length() > 30){
                    Toast.makeText(getApplicationContext(), "Enter Last Name",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)|| email.length() > 30 || Patterns.EMAIL_ADDRESS.matcher(emailPattern).matches()){
                    Toast.makeText(getApplicationContext(), "Enter Email Address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password) || password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)|| password != confirmPassword) {
                    Toast.makeText(getApplicationContext(), "Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Creating User
                dataRef.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Toast.makeText(RegActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(RegActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else
                            {
                            startActivity(new Intent(RegActivity.this, Main2Activity.class));
                            finish();
                            }

                    }
                });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity (new Intent(RegActivity.this, Main4Activity.class));
                finish();
            }
        });

    }
}
