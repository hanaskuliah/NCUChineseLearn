package com.barakiha.chineseeasy1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CharEvaluationActivity extends AppCompatActivity {

    private TextView GradeResult;
    private TextView RememberedStats;
    private Button BackMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_evaluation);

        GradeResult=(TextView)findViewById(R.id.grade_result);
        RememberedStats=(TextView)findViewById(R.id.remembered_stats);
        BackMenu=(Button)findViewById(R.id.back_menu);

        BackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        Intent mIntent = getIntent();
        int numOfChars = mIntent.getIntExtra("numOfChars", 0);
        int numOfRemembered = mIntent.getIntExtra("numOfRemembered", 0);
        float numOfCharFloat=numOfChars;

        float myfloatvariable=(float) (numOfRemembered/numOfCharFloat)*100;
       // Toast.makeText(getApplicationContext(),"is "+myfloatvariable,Toast.LENGTH_SHORT).show();
        String mytext= String.format( "%.1f", myfloatvariable );
        GradeResult.setText(mytext);
        RememberedStats.setText(numOfRemembered+" / "+numOfChars);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}
