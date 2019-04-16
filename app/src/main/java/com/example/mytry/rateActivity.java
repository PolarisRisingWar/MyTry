package com.example.mytry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;

public class rateActivity extends AppCompatActivity implements View.OnClickListener,Runnable {

    float RMB, result;
    float currentRate = 0, newEuroRate = 0, newDollarRate = 0, newWonRate = 0;
    TextView opNum;
    EditText ipRmb;
    Button opEuro, opDollar, opWon, changeRate;
    String TAG = "rateActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ipRmb = findViewById(R.id.ipRMB);
        opNum = findViewById(R.id.opNum);

        opEuro = findViewById(R.id.opEuro);
        opEuro.setOnClickListener(this);
        opDollar = findViewById(R.id.opDollar);
        opDollar.setOnClickListener(this);
        opWon = findViewById(R.id.opWon);
        opWon.setOnClickListener(this);

        //更改汇率功能特区
        changeRate = findViewById(R.id.changeRate);
        changeRate.setOnClickListener(this);

        Intent intent = getIntent();
        newEuroRate = intent.getFloatExtra("newEuroRate", 0);
        newDollarRate = intent.getFloatExtra("newDollarRate", 0);
        newWonRate = intent.getFloatExtra("newWonRate", 0);

        SharedPreferences rateData = getSharedPreferences("rateData", 0);
        newEuroRate = rateData.getFloat("newEuroRate", 0);
        newDollarRate = rateData.getFloat("newDollarRate", 0);
        newWonRate = rateData.getFloat("newWonRate", 0);
        Log.i(TAG, "三大汇率" + newEuroRate + "," + newDollarRate + "," + newWonRate);

        Thread subThread = new Thread(this);
        subThread.start();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    String string = (String) msg.obj;
                    Log.i(TAG, "handleMessage: " + string);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.changeRate) {
                Intent intent = new Intent(this, com.example.mytry.changeRate.class);
                startActivityForResult(intent, 1);//此处用这个代码，点击changeRate按钮跳转到changeRate.java界面
            } else {
                RMB = Float.parseFloat(ipRmb.getText().toString());
                switch (v.getId()) {
                    case R.id.opEuro:
                        if (newEuroRate == 0) {
                            currentRate = 7.55f;
                        } else {
                            currentRate = newEuroRate;
                        }
                        break;
                    case R.id.opDollar:
                        if (newDollarRate == 0) {
                            currentRate = 6.71f;
                        } else {
                            currentRate = newDollarRate;
                        }
                        break;
                    case R.id.opWon:
                        if (newWonRate == 0) {
                            currentRate = 0.0059f;
                        } else {
                            currentRate = newWonRate;
                        }
                        break;
                    default:
                        break;
                }
                result = RMB / currentRate;
                DecimalFormat df = new DecimalFormat("#.00");
                opNum.setText(df.format(result));

            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(rateActivity.this, "请检查您是否没有输入数字", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_toC2F) {
            Intent intent = new Intent(this, com.example.mytry.C2FActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_toNewTeam) {
            Intent intent = new Intent(this, com.example.mytry.newTeamActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void run() {
        Log.i(TAG, "run: Run方法在运行");
        for (int i = 1; i < 6; i++) {
            Log.i(TAG, "run: " + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message msg = handler.obtainMessage();
        msg.what = 5;
        msg.obj = "Hello! I'm a message from run()!";
        handler.sendMessage(msg);

        //获取网络数据
        try {
            URL url = new URL("https://user.qzone.qq.com/1499695895/infocenter");
            HttpsURLConnection https=(HttpsURLConnection)url.openConnection();
            //https.connect();
            //https.setRequestMethod("GET");
            InputStream in=https.getInputStream();
            String html=inputStream2String(in);
            Log.i(TAG, "run: html="+html);
        } catch (MalformedURLException e) {
            Log.e(TAG,Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e(TAG,Log.getStackTraceString(e));
        }
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"UTF-8");
        for(;;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}
