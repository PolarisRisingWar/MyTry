package com.example.mytry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<String> data=new ArrayList<String>();
    private String TAG="MyListActivity";
    ArrayAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListView listView=findViewById(R.id.mylist);
        //初始化数据
        for(int i=0;i<10;i++){
            data.add("item"+i);
        }

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));//当没有数据时显示此条
        //我们之前的做法是显示“wait...”。但是不建议使用那种方法
        //这种方法是在布局中创建一个不可见控件，然后setEmptyView

        //点击某一条数据后就删除该条数据
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> listv, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position"+position);
        Log.i(TAG, "onItemClick: parent"+listv);//listv是个mylist对象，一个listview控件
        //adapter.remove(listv.getItemIdAtPosition(position));//这一句是老师给的代码但是不知道为什么用不了
        adapter.remove(adapter.getItem(position));
        //这个是仅限于ArrayAdapter的。如果用自定义（SimpleAdapter）的话，就会没有这个方法
        //adapter.notifyDataSetChanged();//这一句是可以缺省的
    }
}
