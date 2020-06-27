package com.example.kbc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PageRange;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView question;
    public Button option1;
    public Button option2;
    public Button option3;
    public Button option4;
    private List<Question> questionList;
    private int score;
    private int quesNum;
    public static final int CODE = 101;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
//        setting buttons and text view. Linking with layout file
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        score = 0;

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestionList();
    }

    private void getQuestionList()
    {

        questionList= new ArrayList<>();
        questionList.add(new Question("Question 1","A","B","C","D",2));
        questionList.add(new Question("Question 2","B","C","D","A",2));
        questionList.add(new Question("Question 3","C","D","A","B",2));
        questionList.add(new Question("Question 4","D","B","A","C",2));
        questionList.add(new Question("Question 5","A","C","B","D",2));
        questionList.add(new Question("Question 6","C","B","A","D",2));

        setQuestion();
    }

    private void setQuestion()
    {

        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());
        quesNum = 0;

    }
    @Override
    public void onClick(View view) {

        int selectedOption =0;

        switch (view.getId())
        {

            case R.id.option1:
                selectedOption = 1;
                break;

            case R.id.option2:
                selectedOption =2;
                break;
            case R.id.option3:
                selectedOption =3;
                break;

            case R.id.option4:
                selectedOption =4;
                break;
        }

        checkAnswer(selectedOption,view);
    }

    private void checkAnswer(int selectedOption, View view)
    {

        if(selectedOption == questionList.get(quesNum).getCorrectAns())
        {
            //Right answer

            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            intent.putExtra("Score",score);
            intent.putExtra("QUES_NUM", quesNum);
            startActivityForResult(intent, CODE);


        }
        else
        {
            //Wrong answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(quesNum).getCorrectAns())
            {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }
        changeQuestion();

    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == RESULT_OK) {
            quesNum = data.getExtras().getInt("Quesnumber");
            changeQuestion();
        }


    }



    public void changeQuestion()
    {

        if(quesNum < questionList.size() - 1)
        {
            quesNum++;
            playAnim(question,0, 0);
            playAnim(option1,0, 1);
            playAnim(option2,0, 2);
            playAnim(option3,0, 3);
            playAnim(option4,0, 4);

        }
        else
        {
            //Display Score
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            startActivity(intent);

        }

    }

    private void playAnim(final View view, final int value, final int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationEnd(Animator animator) {
                if(value == 0)
                {
                    switch (viewNum)
                    {
                        case 0:
                            ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                            break;
                        case 1:
                            ((Button)view).setText(questionList.get(quesNum).getOptionA());
                            break;
                        case 2:
                            ((Button)view).setText(questionList.get(quesNum).getOptionB());
                            break;
                        case 3:
                            ((Button)view).setText(questionList.get(quesNum).getOptionC());
                            break;
                        case 4:
                            ((Button)view).setText(questionList.get(quesNum).getOptionD());
                            break;

                    }

                    if(viewNum != 0)
                    {

                        ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4848FE")));
                    }

                    playAnim(view,1,viewNum);
                }
                else
                {

                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
