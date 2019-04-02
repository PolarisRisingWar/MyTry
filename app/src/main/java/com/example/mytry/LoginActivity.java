package com.example.mytry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView out;
    EditText ipname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        out=findViewById(R.id.pfLabel);
        out.setText("登录界面是我");

        ipname=findViewById(R.id.nameText);
        String name=ipname.getText().toString();

        EditText pwText=findViewById(R.id.pwText);
        String pw=pwText.getText().toString();

        Button btn=findViewById(R.id.lgBt);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        out.setText(ipname.getText().toString()+"登录成功！");
    }
}
