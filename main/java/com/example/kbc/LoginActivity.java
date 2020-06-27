package com.example.kbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private Button loginbtn;

    private ProgressBar loginprogressBar;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_etv);
        password = findViewById(R.id.password_etv);

        ImageButton googlesignin = findViewById(R.id.google_ibtn);
        ImageButton facebooksigin = findViewById(R.id.facebook_ibtn);

        loginbtn = findViewById(R.id.login_btn);

        loginprogressBar = findViewById(R.id.login_PB);

        TextView signuptv = findViewById(R.id.signUp_tv);
        TextView forgotpassword = findViewById(R.id.forgotPassword_tv);

        firebaseAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkinputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkinputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailandPassword();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotIntent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(forgotIntent);
                finish();
            }
        });



        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signupIntent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signupIntent);
                finish();
            }
        });


    }

    private void checkinputs() {
        if(!TextUtils.isEmpty(email.getText().toString())){
            if(!TextUtils.isEmpty(password.getText().toString())){

                loginbtn.setEnabled(true);
                loginbtn.setTextColor(getResources().getColor(R.color.colorAccent));
            }else{
                loginbtn.setEnabled(false);

            }

        }else{
            loginbtn.setEnabled(false);

        }
    }
    private void checkEmailandPassword() {
        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if(email.getText().toString().matches(emailpattern)){
           if(password.length()>=6){
               loginprogressBar.setVisibility(View.VISIBLE);

               firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){

                                   Intent mainIntent = new Intent(LoginActivity.this,HomeActivity.class);
                                   startActivity(mainIntent);
                                   finish();

                               }else{
                                   loginprogressBar.setVisibility(View.INVISIBLE);
                                   String error = Objects.requireNonNull(task.getException()).getMessage();
                                   Toast.makeText(LoginActivity.this,error, Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
           }else{
               Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();

           }

       }else{
           Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();

       }

    }
}
