package com.cateringpartner.cyhb.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.application.ApplicationUtil;
import com.cateringpartner.cyhb.basket.BasketFragment;
import com.cateringpartner.cyhb.helper.BottomNavigationViewHelper;
import com.cateringpartner.cyhb.home.HomeFragment;
import com.cateringpartner.cyhb.mine.MineFragment;
import com.cateringpartner.cyhb.myorder.MyOrderFragment;
import com.cateringpartner.cyhb.promotion.PromotionFragment;
import com.cateringpartner.cyhb.util.Util;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region 变量
    private int BASKETFLAG = 2;
    private int MYORDERFLAG = 4;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    private Button btn;
    private TextView tv;
    public Util util;
    public ApplicationUtil applicationUtil;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        util = new Util();
        util.setActivityFlag(1);

        // 初始化全局变量
        applicationUtil = new ApplicationUtil();
        applicationUtil.setActivityFlag(getString(R.string.user_mainactivity_home));

        initIDandView();
    }

    private void initIDandView(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Resources resource=(Resources)getBaseContext().getResources();
        ColorStateList csl=(ColorStateList)resource.getColorStateList(R.color.navigation_menu_item_color);
        bottomNavigationView.setItemTextColor(csl);
        bottomNavigationView.setItemIconTintList(csl);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        fragments.add(new HomeFragment());
        fragments.add(new BasketFragment());
        fragments.add(new PromotionFragment());
        fragments.add(new MyOrderFragment());
        fragments.add(new MineFragment());
        showFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentIndex = 0;
                    util.setActivityFlag(1);
                    applicationUtil.setActivityFlag(getString(R.string.user_mainactivity_home));
                    showFragment();
                    return true;
                case R.id.navigation_shopbasket:
                    currentIndex = 1;
                    util.setActivityFlag(2);
                    applicationUtil.setActivityFlag(getString(R.string.user_mainactivity_basket));
                    /*
                    try{
                        ft = fm.beginTransaction();
                        ft.remove(fragments.get(currentIndex));
                        ft.add(new BasketFragment(),""+currentIndex);
                        ft.commit();
                    }
                    catch (Exception e){

                    }
                    */
                    showFragment();
                    return true;
                case R.id.navigation_promotion:
                    currentIndex = 2;
                    util.setActivityFlag(3);
                    applicationUtil.setActivityFlag(getString(R.string.user_mainactivity_promotion));
                    showFragment();
                    return true;
                case R.id.navigation_myorder:
                    util.setActivityFlag(4);
                    applicationUtil.setActivityFlag(getString(R.string.user_mainactivity_order));
                    currentIndex = 3;
                    showFragment();
                    return true;
                case R.id.navigation_mine:
                    util.setActivityFlag(5);
                    applicationUtil.setActivityFlag(getString(R.string.user_mainactivity_mine));
                    currentIndex = 4;
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
        try{
            if(currentIndex == 1){
                fragments.remove(1);
                fragments.add(1,new BasketFragment());
            }
            if(currentIndex == 3){
                fragments.remove(3);
                fragments.add(3,new MyOrderFragment());
            }
            if(currentIndex == 4){
                fragments.remove(4);
                fragments.add(4,new MineFragment());
            }
        }
        catch (Exception e){

        }
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            new BottomDialog.Builder(this)
                    .setTitle("")
                    .setContent("退出？")
                    .setPositiveText("确定")
                    .setPositiveBackgroundColorResource(R.color.colorPrimary)
                    //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                    .setPositiveTextColorResource(android.R.color.white)
                    //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                    .onPositive(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(BottomDialog dialog) {
                            finish();
                        }
                    })
                    .setNegativeText("取消")
                    .setNegativeTextColorResource(R.color.colorAccent)
                    //.setNegativeTextColor(ContextCompat.getColor(this, R.color.colorAccent)
                    .onNegative(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(BottomDialog dialog) {
                            //Log.d("BottomDialogs", "Do something!");

                        }
                    }).show();
        }
        return true;
    }
}
