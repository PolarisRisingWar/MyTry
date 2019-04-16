package com.example.mytry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class rateActivity extends AppCompatActivity implements View.OnClickListener {

    double RMB,result;
    double currentRate=0,newEuroRate=0,newDollarRate=0,newWonRate=0;
    TextView opNum;
    EditText ipRmb;
    Button opEuro,opDollar,opWon,changeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ipRmb=findViewById(R.id.ipRMB);
        opNum=findViewById(R.id.opNum);

        opEuro=findViewById(R.id.opEuro);
        opEuro.setOnClickListener(this);
        opDollar=findViewById(R.id.opDollar);
        opDollar.setOnClickListener(this);
        opWon=findViewById(R.id.opWon);
        opWon.setOnClickListener(this);

        //更改汇率功能特区
        changeRate=findViewById(R.id.changeRate);
        changeRate.setOnClickListener(this);

        Intent intent = getIntent();
        newEuroRate = intent.getDoubleExtra("newEuroRate",0);
        newDollarRate = intent.getDoubleExtra("newDollarRate",0);
        newWonRate = intent.getDoubleExtra("newWonRate",0);
    }

    @Override
    public void onClick(View v) {
            try {
                if(v.getId()==R.id.changeRate){
                    Intent intent = new Intent(this, com.example.mytry.changeRate.class);
                    startActivity(intent);//此处用这个代码，点击changeRate按钮跳转到changeRate.java界面
                }
                else {
                    RMB = Double.parseDouble(ipRmb.getText().toString());
                        switch (v.getId()) {
                            case R.id.opEuro:
                                if(newEuroRate==0) {
                                    currentRate = 7.55;
                                }
                                else{
                                    currentRate=newEuroRate;
                                }
                                break;
                            case R.id.opDollar:
                                if(newDollarRate==0){
                                    currentRate=6.71;
                                }
                                else{
                                    currentRate=newDollarRate;
                                }
                                break;
                            case R.id.opWon:
                                if(newWonRate==0){
                                    currentRate=0.0059;
                                }
                                else{
                                    currentRate=newWonRate;
                                }
                                break;
                            default:
                                break;
                        }
                        result=RMB/currentRate;
                        DecimalFormat df = new DecimalFormat("#.00");
                        opNum.setText(df.format(result));

                }
            } catch (Exception e) {
                Toast toast = Toast.makeText(rateActivity.this, "请检查您是否没有输入数字", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_toC2F) {
            Intent intent = new Intent(this, com.example.mytry.C2FActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.menu_toNewTeam){
            Intent intent = new Intent(this, com.example.mytry.newTeamActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
