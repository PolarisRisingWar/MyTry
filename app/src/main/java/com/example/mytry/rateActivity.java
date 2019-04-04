package com.example.mytry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
    double currentRate=0;
    TextView opNum,currentType;
    EditText ipRmb,currentRateR;
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

        currentRateR=findViewById(R.id.currentRate);
    }

    @Override
    public void onClick(View v) {

        //如果是点了更改汇率按钮

            //如果是点了直接兑换按钮
            try {
                if(v.getId()==R.id.changeRate){
                    currentRate=Double.parseDouble(currentRateR.getText().toString());
                    currentRate=1/currentRate;
                }
                else {
                    RMB = Double.parseDouble(ipRmb.getText().toString());
                    if(currentRate==0||currentRateR.getText().toString().equals("")) {
                        switch (v.getId()) {
                            case R.id.opEuro:
                                currentRate=7.55;
                                //currentType.setText("欧元");
                                break;
                            case R.id.opDollar:
                                currentRate=6.71;
                                //currentType.setText("美元");
                                break;
                            case R.id.opWon:
                                currentRate=0.0059;
                                //currentType.setText("韩元");
                                break;
                            default:
                                break;
                        }
                    }


                    //改“你要兑换的货币”这一项
                    /*switch (v.getId()) {
                        case R.id.opEuro:currentType.setText("欧元");
                            break;
                        case R.id.opDollar:currentType.setText("美元");
                            break;
                        case R.id.opWon:currentType.setText("韩元");
                            break;
                        default:
                            break;
                    }*/




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
}
