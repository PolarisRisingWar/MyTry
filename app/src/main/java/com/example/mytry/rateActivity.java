package com.example.mytry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class rateActivity extends AppCompatActivity implements View.OnClickListener,Runnable {

    float RMB, result;
    float currentRate = 0, newEuroRate = 0, newDollarRate = 0, newWonRate = 0;
    TextView opNum;
    EditText ipRmb;
    Button opEuro, opDollar, opWon, changeRate;
    String TAG = "rateActivity";
    Handler handler;
    
    private String updateDate="";

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

        //获取rateData里保存的数据
        SharedPreferences rateData = getSharedPreferences("rateData", 0);
        newEuroRate = rateData.getFloat("newEuroRate", 0);
        newDollarRate = rateData.getFloat("newDollarRate", 0);
        newWonRate = rateData.getFloat("newWonRate", 0);
        Log.i(TAG, "三大汇率" + newEuroRate + "," + newDollarRate + "," + newWonRate);
        
        updateDate=rateData.getString("update_date","");
        //获取当前系统时间
        //new Date不建议用
        Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM-dd");//yyyyMMdd也行，重要的是8位
        final String todayStr=sdf.format(today);
        Log.i(TAG, "onCreate: rateData="+updateDate);
        Log.i(TAG, "onCreate: rateData="+todayStr);
        //判断时间。系统时间与储存的时间是否一致（我们的目标是一天一更新）
        if(!todayStr.equals((updateDate))){
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread subThread = new Thread(this);
            subThread.start();
        }
        else Log.i(TAG, "onCreate: 不需要更新");

        

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    Bundle bdl= (Bundle) msg.obj;
                    newEuroRate=bdl.getFloat("newEuroRate");
                    newDollarRate=bdl.getFloat("newDollarRate");
                    newWonRate=bdl.getFloat("newWonRate");

                    Log.i(TAG, "美元汇率: "+newDollarRate+"  欧元汇率："+newEuroRate+"  韩元汇率："+newWonRate);

                    //保存更新的日期
                    SharedPreferences  rateData = getSharedPreferences("rateData", 0);
                    SharedPreferences.Editor editor=rateData.edit();
                    editor.putFloat("newDollarRate",newDollarRate);
                    editor.putFloat("newWonRate",newWonRate);
                    editor.putFloat("newEuroRate",newEuroRate);
                    editor.putString("update_date",todayStr);
                    editor.apply();//commit也可以

                    Toast.makeText(rateActivity.this,"汇率已更新",Toast.LENGTH_SHORT).show();
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
        } else if(id==R.id.open_list){//这个是那个瓶子图表
            //打开列表窗口
            Intent list=new Intent(this,RateListActivity.class);
            startActivity(list);

            //测试数据库
           /* RateItem item1=new RateItem("aaaa","123");
            RateManager manager=new RateManager(this);
            manager.add(item1);
            manager.add(new RateItem("bbbb","23.5"));
            Log.i(TAG, "onOptionsItemSelected: 写入数据完毕");

            //查询所有数据
            List<RateItem> testList=manager.listAll();
            for (RateItem i:testList){
                Log.i(TAG, "onOptionsItemSelected: 取出数据Name="+i.getCurName()+" Rate="+i.getCurRate()+" id="+i.getId());
            }*/
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void run() {
        //此处将实时汇率放进bundle里
        Bundle bundle=new Bundle();

        /*Log.i(TAG, "run: Run方法在运行");
        for (int i = 1; i < 6; i++) {
            Log.i(TAG, "run: " + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        这一部分是输出1,2,3,4,5等，以及休眠2毫秒之类的东西*/



        //获取网络数据
        /*try {
            //URL url = new URL("http://www.boc.cn/sourcedb/whpj/");
            //URL url=new URL("http://usd-cny.com/icbc.htm");
            URL url=new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection https=(HttpURLConnection)url.openConnection();
            //https.connect();
            //https.setRequestMethod("GET");
            InputStream in=https.getInputStream();
            String html=inputStream2String(in);
            Log.i(TAG, "run: html="+html);
            Document doc=Jsoup.parse(html);
        } catch (MalformedURLException e) {
            Log.e(TAG,Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e(TAG,Log.getStackTraceString(e));
        }*/

        //使用Jsoup包解析网站代码
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            //doc=Jsoup.parse(html);
            //这一句可以提取html

            //获取title
            Log.i(TAG,"title:"+doc.title());

            //获取table中的多个表格（本次运行过程中只有一个table）
            Elements tables=doc.getElementsByTag("table");
            int i=1;
            for(Element table:tables){
                //显示全部表格：Log.i(TAG, "table["+i+"]="+table);
                i++;
            }
            Element table1=tables.get(0);
            //显示我们需要的这个table：Log.i(TAG, "table1="+table1);

            //获取td中的内容
            Elements tds=table1.getElementsByTag("td");
            //过滤出我们所需要的数据
            for(int s=0;s<tds.size();s+=6){
                Element td1=tds.get(s);//得到币种
                Element td2=tds.get(s+5);//得到该币种的折算价
                Log.i(TAG, "text="+td1.text()+"  折算价："+td2.text());
                String str1=td1.text();
                String val=td2.text();

                if("美元".equals(str1)){
                    bundle.putFloat("newDollarRate",Float.parseFloat(val)/100f);
                }
                else if("韩元".equals(str1)){
                    bundle.putFloat("newWonRate",Float.parseFloat(val)/100f);
                }
                else if("欧元".equals(str1)){
                    bundle.putFloat("newEuroRate",Float.parseFloat(val)/100f);
                }
            }
            /*for(Element td:tds){
                //展示所有td的内容：Log.i(TAG, "td: "+td);
                //在这种情况下，td.text()和td.html()获得的结果是一样的，但有时在td里面的内容里面也有HTML标签的时候就会不一样。
                //另一种思路是先取出tr，然后再从中拆分td

            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        //bundle中保存了所获取的汇率
        //获取msg对象，用于返回主线程
        Message msg = handler.obtainMessage();
        msg.what = 5;
        //msg.obj = "Hello! I'm a message from run()!";
        msg.obj=bundle;
        handler.sendMessage(msg);

    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        for(;;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}
