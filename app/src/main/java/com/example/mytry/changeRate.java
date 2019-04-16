package com.example.mytry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changeRate extends AppCompatActivity implements View.OnClickListener {

    Button back,backWith;
    EditText euroRate,dollarRate,wonRate;
    float newEuroRate,newDollarRate,newWonRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_rate);

        back=findViewById(R.id.backToMain);
        back.setOnClickListener(this);
        backWith=findViewById(R.id.backWithNumber);
        backWith.setOnClickListener(this);

        euroRate=findViewById(R.id.euroRate);
        dollarRate=findViewById(R.id.dollarRate);
        wonRate=findViewById(R.id.wonRate);

    }

    public void onClick(View view){
        Intent intent = new Intent(this, com.example.mytry.rateActivity.class);
        if(view.getId()==R.id.backToMain){
            startActivity(intent);//此处跳转回主页
            //finish();
            //保留finish，因为我还没看懂这一句是干什么用的
        }
        else if(view.getId()==R.id.backWithNumber){
            try{
                newEuroRate = Float.parseFloat(euroRate.getText().toString());
                newDollarRate = Float.parseFloat(dollarRate.getText().toString());
                newWonRate = Float.parseFloat(wonRate.getText().toString());
                intent.putExtra("newEuroRate",newEuroRate);
                intent.putExtra("newDollarRate",newDollarRate);
                intent.putExtra("newWonRate",newWonRate);
                startActivity(intent);

                SharedPreferences rateData= getSharedPreferences("rateData", 0);
                SharedPreferences.Editor editor = rateData.edit();
                editor.putFloat("newEuroRate",newEuroRate);
                editor.putFloat("newDollarRate",newDollarRate);
                editor.putFloat("newWonRate",newWonRate);
                editor.apply();
            }
            catch (Exception e){
                Toast.makeText(changeRate.this,"您必须要输入所有汇率！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
