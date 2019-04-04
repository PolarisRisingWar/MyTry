package com.example.mytry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class newTeamActivity extends AppCompatActivity implements View.OnClickListener {

    TextView scoreA,scoreB;
    String p="0";
    String c,cb;
    int cn,cnb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);

        scoreA=findViewById(R.id.scoreA);
        scoreB=findViewById(R.id.scoreB);

        Button plus3=findViewById(R.id.plus3);
        plus3.setOnClickListener(this);
        Button plus2=findViewById(R.id.plus2);
        plus2.setOnClickListener(this);
        Button plus1=findViewById(R.id.plus1);
        plus1.setOnClickListener(this);
        Button plus3b=findViewById(R.id.plus3b);
        plus3b.setOnClickListener(this);
        Button plus2b=findViewById(R.id.plus2b);
        plus2b.setOnClickListener(this);
        Button plus1b=findViewById(R.id.plus1b);
        plus1b.setOnClickListener(this);
        Button reset=findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        c=scoreA.getText().toString();
        cn=Integer.parseInt(c);
        cb=scoreB.getText().toString();
        cnb=Integer.parseInt(cb);
        switch (v.getId()){
            case R.id.plus3:
                scoreA.setText((int)(cn+3)+"");
                break;
            case R.id.plus2:
                scoreA.setText((int)(cn+2)+"");
                break;
            case R.id.plus1:
                scoreA.setText((int)(cn+1)+"");
                break;
            case R.id.plus3b:
                scoreB.setText((int)(cnb+3)+"");
                break;
            case R.id.plus2b:
                scoreB.setText((int)(cnb+2)+"");
                break;
            case R.id.plus1b:
                scoreB.setText((int)(cnb+1)+"");
                break;
            case R.id.reset:
                scoreA.setText(p);
                scoreB.setText(p);
            default:
                break;
        }


    }
}
