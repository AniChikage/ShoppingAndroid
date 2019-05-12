package com.cateringpartner.cyhb.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.helper.BottomNavigationViewHelper;
import com.cateringpartner.cyhb.mine.MineAdAndShopFragment;
import com.cateringpartner.cyhb.money.MoneyFragment;
import com.cateringpartner.cyhb.rcvorder.RcvOrderFragment;
import com.cateringpartner.cyhb.refund.RefundFragment;
import com.cateringpartner.cyhb.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdministrator extends AppCompatActivity {

    private int BASKETFLAG = 2;
    private int MYORDERFLAG = 4;
    private int RCVORDERFLAG = 10;
    private int RefundORDERFLAG = 15;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    private Button btn;
    private TextView tv;
    public Util util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.activity_main_admin);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        util = new Util();
        util.setActivityFlag(1);
        initIDandView();
    }

    private void initIDandView(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        fragments.add(new RcvOrderFragment());
        fragments.add(new RefundFragment());
        fragments.add(new MoneyFragment());
        fragments.add(new MineAdAndShopFragment());
        showFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_rcvorder:
                    util.setActivityFlag(RCVORDERFLAG);
                    currentIndex = 0;
                    showFragment();
                    return true;
                case R.id.navigation_refund:
                    util.setActivityFlag(RefundORDERFLAG);
                    currentIndex = 1;
                    showFragment();
                    return true;
                case R.id.navigation_money:
                    util.setActivityFlag(1001);
                    currentIndex = 2;
                    showFragment();
                    return true;
                case R.id.navigation_my:
                    util.setActivityFlag(1001);
                    currentIndex = 3;
                    showFragment();
                    return true;
            }

            return false;
        }

    };

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){
        ft = fm.beginTransaction();
        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            ft.hide(currentFragment)
                    .add(R.id.content,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag
        }else{
            ft.hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }
        currentFragment = fragments.get(currentIndex);
        ft.commit();
    }
}
