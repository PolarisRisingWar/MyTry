package com.example.mytry;

import android.app.ListActivity;
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
import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        //因为父类中已经包含有一个布局

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
        Document doc = null;
        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"title:"+doc.title());
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message msg=handler.obtainMessage(7);//这个数字可以随便改
        msg.obj=retList;
        handler.sendMessage(msg);
    }
}
