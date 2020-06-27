package com.example.kbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class ScoreActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private Button score5k;
    private Button score10k;
    private Button score20k;
    private int scoreholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score5k = (Button) findViewById(R.id.score1);
        score10k = (Button) findViewById(R.id.score2);
        score20k = (Button) findViewById(R.id.score3);


        Bundle extras = getIntent().getExtras();
        int score = extras.getInt("Score");
        int question = extras.getInt("QUES_NUM");

        if(extras != null)
        {
            if (score == 1)
            {
                score5k.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                if (score ==2)
                {
                    score5k.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    score10k.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    if (score ==3)
                    {
                        score5k.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        score10k.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        score20k.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    }
                }

            }
            else
            {

            }

        }

        Intent intent = getIntent();
        intent.putExtra("Quesnumber",question);
        setResult(RESULT_OK, intent);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScoreActivity.this,QuestionActivity.class);
                startActivity(intent);
            }
        }, 4000);

    }

}
