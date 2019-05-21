package com.example.mytry;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    //本activity是拿来做列表的
    /*列表只需要创建可见的那些控件，超过屏幕的空间或被回收变成下一个将要进入屏幕的控件。这个功能是安卓自己提供的
    * 我们要做的就是搞一个adapter，adapter是用来管理data和list的关系
    * 我们把数据给adapter
    * 要创建列表，首先要一个listview控件，然后用adapter当桥梁。有了这三种东西以后就能完成一个列表
    * 如果界面里面全是list，就不用自己拉listview了，用ListActivity就行
    * 也可以自定义列表*/
//把activity做成列表，有一种方式是在xml中直接拖入listview

    String TAG = "RateListActivity";

    String data[]={"wait..."};//可以通过循环、线程保存往里面加数据
    Handler handler;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        //因为父类中已经包含有一个布局

        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);

        final List<String> list1=new ArrayList<String>();
        for(int i=1;i<100;i++){
            list1.add("item"+i);
        }

        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        /*第二个参数是布局，因为列表中每一条都是一个控件
        * 通常获得控件的时候是用R去找资源。彼处的R是工程中的资源，此处的R是安卓平台中的资源。这样是区分
        * 这是个简单的布局*/
        /*<String>是范型*/
        setListAdapter(adapter);//当前类是继承于父类，所以能用这个方法。把当前界面用adapter来管理

        Thread thread=new Thread(this);
        thread.start();

        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==7){
                    List<String> list2=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程中
        List<String> retList=new ArrayList<String>();

        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);

        if(curDateStr.equals(logDate)) {
            //如果相等，则不从网络中获取数据
            Log.i("run", "日期相等，从数据库中获取数据");
            RateManager manager=new RateManager(this);
            for(RateItem item:manager.listAll()){
                retList.add(item.getCurName()+"-->"+item.getCurRate());
            }
        }else {
            //从网络获取数据
            Log.i("run", "日期不相等，从网络中获取在线数据");
            Document doc = null;
            try {
                Thread.sleep(3);
                doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                Log.i(TAG,"title:"+doc.title());
                List<RateItem> rateList = new ArrayList<RateItem>();//上面那个retList是为了主线程的，这个是为了写数据库的
                Elements tables=doc.getElementsByTag("table");
            /*int i=1;
            for(Element table:tables){
                i++;
            }*/
                Element table1=tables.get(0);
                Elements tds=table1.getElementsByTag("td");
                //过滤出我们所需要的数据
                for(int s=0;s<tds.size();s+=6){
                    Element td1=tds.get(s);//得到币种
                    Element td2=tds.get(s+5);//得到该币种的折算价
                    Log.i(TAG, "text="+td1.text()+"  折算价："+td2.text());
                    String str1=td1.text();
                    String val=td2.text();
                    retList.add(td1.text()+"  折算价："+td2.text());
                    rateList.add(new RateItem(str1,val));
                }
                //把数据写入到数据库中
                RateManager manager=new RateManager(this);
                manager.deleteAll();
                manager.addAll(rateList);
                //记录更新日期
                //更新记录日期
                SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(DATE_SP_KEY, curDateStr);
                edit.commit();
                Log.i("run","更新日期结束： " + curDateStr);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        Message msg=handler.obtainMessage(7);//这个数字可以随便改
        msg.obj=retList;
        handler.sendMessage(msg);
    }
}
