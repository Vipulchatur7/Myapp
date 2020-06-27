package com.example.kbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button playnowbtn = findViewById(R.id.playnow_btn);
        Button challangebtn = findViewById(R.id.challange_btn);

        ImageView playnowibtn = findViewById(R.id.playnow_ibtn);
        ImageView challageibtn = findViewById(R.id.challange_ibtn);

        playnowibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizIntent = new Intent(HomeActivity.this, Question.class);
                startActivity(quizIntent);
                finish();
            }
        });

        playnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizINtent = new Intent(HomeActivity.this,QuestionActivity.class);
                startActivity(quizINtent);
                finish();

            }
        });
    }
}
