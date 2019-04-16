package com.example.mytry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

public class C2FActivity extends AppCompatActivity implements View.OnClickListener{

    TextView out;
    EditText cValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c2f);

        out=findViewById(R.id.show);

        cValue=findViewById(R.id.ipt);

        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(this);
    }
    public void onClick(View v) {
        String c=cValue.getText().toString();
        double cNum=Double.parseDouble(c);
        double f=cNum*1.8+32;
        BigDecimal   b   =   new   BigDecimal(f);
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        out.setText("该摄氏度转换成的值是"+f1);
    }
}
