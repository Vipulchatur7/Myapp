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
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    private EditText recoveryemailetv;
    private Button sumbitbtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        recoveryemailetv = findViewById(R.id.forgotpass_email_etv);
        TextView gobacktv = findViewById(R.id.goback_tv);
        TextView messagetv = findViewById(R.id.message_tv);
        sumbitbtn = findViewById(R.id.sumbit_btn);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        gobacktv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        recoveryemailetv.addTextChangedListener(new TextWatcher() {
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

        sumbitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(recoveryemailetv.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.VISIBLE);



                                }else{
                                    String error = Objects.requireNonNull(task.getException()).getMessage();
                                    Toast.makeText(ForgotPassword.this,error,Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }
        });
    }

    private void checkinputs (){
        if(!TextUtils.isEmpty(recoveryemailetv.getText())){

            sumbitbtn.setEnabled(true);
            sumbitbtn.setTextColor(getResources().getColor(R.color.colorAccent));

        }else{
            sumbitbtn.setEnabled(false);

        }
    }
}
