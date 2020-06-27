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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private EditText signupemail;
    private EditText fullname;
    private EditText signuppassword;
    private EditText confirmpassword;

    private Button signup;

    private ProgressBar signupprogressbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupemail = findViewById(R.id.signupemail_etv);
        fullname = findViewById(R.id.fullname_etv);
        signuppassword = findViewById(R.id.password_etv);
        confirmpassword = findViewById(R.id.confirmpassword_etv);

        signup = findViewById(R.id.signup_btn);
        TextView signintext = findViewById(R.id.signin_tv);
        signupprogressbar = findViewById(R.id.signup_PB);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();

        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signuppassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkEmailandPassword();
            }
        });


        signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signinIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(signinIntent);
                finish();
            }
        });
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(signupemail.getText())){
            if(!TextUtils.isEmpty(fullname.getText())){
                if(!TextUtils.isEmpty(signuppassword.getText()) && signuppassword.length()>=6){
                    if(!TextUtils.isEmpty(confirmpassword.getText())){
                        signup.setEnabled(true);
                        signup.setTextColor(getResources().getColor(R.color.colorAccent));
                    }else{
                        signup.setEnabled(false);
                        signup.setTextColor(Color.argb(50,255,255,255));
                    }

                }else{
                    signup.setEnabled(false);
                    signup.setTextColor(Color.argb(50,255,255,255));
                }

            }else{
                signup.setEnabled(false);
                signup.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            signup.setEnabled(false);
            signup.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void checkEmailandPassword(){
        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if(signupemail.getText().toString().matches(emailpattern)){
            if(signuppassword.getText().toString().equals(confirmpassword.getText().toString() )){
                firebaseAuth.createUserWithEmailAndPassword(signupemail.getText().toString(), signuppassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    signup.setVisibility(View.INVISIBLE);
                                    signupprogressbar.setVisibility(View.VISIBLE);

                                    Map<Object,String> userdata = new HashMap<>();
                                    userdata.put("fullname",fullname.getText().toString());

                                    firebaseFirestore.collection("Useres").add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if(task.isSuccessful()){

                                                Intent homeIntent = new Intent(SignupActivity.this,HomeActivity.class);
                                                startActivity(homeIntent);
                                                finish();

                                            }else{
                                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                                Toast.makeText(SignupActivity.this, "Error1", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });



                                }else{
                                    String error = Objects.requireNonNull(task.getException()).getMessage();
                                    Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                confirmpassword.setError("Password Doesn't Match");
            }

        }else{
            signupemail.setError("Invalid Email");

        }

    }
}
