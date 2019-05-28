package com.example.mytry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrameActivity extends FragmentActivity {
    private Fragment mFragments[];//管理当前fragment
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;//管理切换的一个管理器
    private FragmentTransaction fragmentTransaction;//一个事务
    private RadioButton rbtHome,rbtFunc,rbtSetting;//三个按钮项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment);//这个我是改过名了

        mFragments=new Fragment[3];
        fragmentManager=getSupportFragmentManager();
        mFragments[0]=fragmentManager.findFragmentById(R.id.fragment_main);
        mFragments[1]=fragmentManager.findFragmentById(R.id.fragment_func);
        mFragments[2]=fragmentManager.findFragmentById(R.id.fragment_setting);
        fragmentTransaction=fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();

        rbtHome=(RadioButton)findViewById(R.id.radioHome);
        rbtFunc=(RadioButton)findViewById(R.id.radioFunc);
        rbtSetting=(RadioButton)findViewById(R.id.radioSetting);
        rbtHome.setBackgroundResource(R.drawable.shape3);

        radioGroup=(RadioGroup)findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            String TAG="radioGroup";
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i(TAG, "checkId="+checkedId);
                fragmentTransaction=fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                rbtHome.setBackgroundResource(R.drawable.shape2);
                rbtFunc.setBackgroundResource(R.drawable.shape2);
                rbtSetting.setBackgroundResource(R.drawable.shape2);
                switch (checkedId){
                    case R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        rbtHome.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.radioFunc:
                        fragmentTransaction.show(mFragments[1]).commit();
                        rbtFunc.setBackgroundResource(R.drawable.shape3);
                        break;
                    case R.id.radioSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        rbtSetting.setBackgroundResource(R.drawable.shape3);
                        break;
                        default:
                            break;
                }
            }
        });
    }
}
