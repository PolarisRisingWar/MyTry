package com.example.mytry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class setScoreActivity extends AppCompatActivity{

    TextView score;
    String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_score);

        score=findViewById(R.id.score);
        p= score.getText().toString();  //这个p是score的原始值，也就是0

    }
    public void click3(View view) {
        String c=score.getText().toString();  //这个c是score的当前值
        double cNum=Double.parseDouble(c);  //这个cNum是把c转换成double型
        score.setText((int) (cNum+3)+"");
    }

    public void click2(View view) {
        String c=score.getText().toString();  //这个c是score的当前值
        double cNum=Double.parseDouble(c);  //这个cNum是把c转换成double型
        score.setText((int) (cNum+2)+"");
    }

    public void click1(View view) {
        String c=score.getText().toString();  //这个c是score的当前值
        double cNum=Double.parseDouble(c);  //这个cNum是把c转换成double型
        score.setText((int) (cNum+1)+"");
    }

    public void reset(View view) {
        score.setText(p);
    }
}
