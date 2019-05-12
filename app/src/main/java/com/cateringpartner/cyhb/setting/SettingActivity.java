package com.cateringpartner.cyhb.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.Util;
import com.cateringpartner.cyhb.widget.LoginActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import info.hoang8f.widget.FButton;

/**
 * Created by AniChikage on 2017/6/10.
 */

public class SettingActivity extends Activity{

    private Util util;
    //private TextView tv_update;
    private FButton fButton;
    private EditText et_phone;
    private EditText et_realname;
    private EditText et_username;
    private EditText et_address;
    private EditText et_alipayaccount;
    private TextView tv_logout;
    private TextView tv_update;
    private TextView tv_resetpwd;
    private MaterialSpinner s_usercity;
    private MaterialSpinner s_district;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.setting);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        util = new Util();
        //initIDandView();
    }

    //region 初始化ID
    private void initIDandView(){
        tv_update = (TextView) findViewById(R.id.update);
        tv_resetpwd = (TextView) findViewById(R.id.resetpwd);
        et_phone = (EditText) findViewById(R.id.phone);
        et_realname = (EditText) findViewById(R.id.realname);
        et_username = (EditText) findViewById(R.id.username);
        et_address = (EditText) findViewById(R.id.address);
        et_alipayaccount = (EditText) findViewById(R.id.alipayaccount);
        tv_logout = (TextView) findViewById(R.id.logout);
        s_usercity = (MaterialSpinner) findViewById(R.id.usercity);
        s_district = (MaterialSpinner) findViewById(R.id.district);
        tv_logout.setOnClickListener(onClickListener);
        tv_update.setOnClickListener(onClickListener);
        tv_resetpwd.setOnClickListener(onClickListener);
        new Thread(getUserRealnameRunable).start();
    }
    //endregion

    //region clicklisnter
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.update:
                    new Thread(updateUserRunable).start();
                    break;
                case R.id.logout:
                    logout();
                    break;
                case R.id.resetpwd:
                    resetPwd();
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    //region 重置密码事件
    private void resetPwd(){
        Intent intent = new Intent();
        intent.setClass(this, ResetPwd.class);
        startActivity(intent);
    }
    //endregion

    //region
    private void logout(){
        delFile();
        Toast.makeText(SettingActivity.this,"注销成功，请返回重新登陆",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //删除文件
    public void delFile(){
        File file = new File("/sdcard/com.catering-partner/u.txt");
        if(file.isFile()){
            file.delete();
        }
        file.exists();
        file = new File("/sdcard/com.catering-partner/p.txt");
        if(file.isFile()){
            file.delete();
        }
        file.exists();
    }
    //endregion

    //region 获取用户名
    private Runnable getUserRealnameRunable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_GetUserByUid(util.getGLOBALUSERID());
            Log.e("getUserByPhone result",result);
            Message msg = new Message();
            msg.obj = result;
            getUserRealnameHandler.sendMessage(msg);
        }
    };

    private Handler getUserRealnameHandler = new Handler(){
        public void handleMessage(Message msg) {
            try{
                JSONArray arr = new JSONArray((String)msg.obj);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    et_phone.setText(temp.getString("phone"));
                    et_realname.setText(temp.getString("realname"));
                    et_username.setText(temp.getString("username"));
                    et_address.setText(temp.getString("address"));
                    et_alipayaccount.setText(temp.getString("alipayaccount"));
                    s_usercity.setText(temp.getString("usercity"));
                    s_district.setText(temp.getString("district"));
                }
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };
    //endregion

    //region 更新用户信息
    private Runnable updateUserRunable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.updateUser(
                    et_phone.getText().toString().trim(),
                    et_username.getText().toString().trim(),
                    et_realname.getText().toString().trim(),
                    et_alipayaccount.getText().toString().trim(),
                    s_usercity.getText().toString().trim(),
                    s_district.getText().toString().trim(),
                    et_address.getText().toString().trim(),
                    util.getGLOBALUSERID()
                    );
            Log.e("updateUser result",result);
            Message msg = new Message();
            msg.obj = result;
            updateUserHandler.sendMessage(msg);
        }
    };

    private Handler updateUserHandler = new Handler(){
        public void handleMessage(Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                if(jsonObject.getString("status").equals("1")){
                    Toast.makeText(SettingActivity.this,"更新成功", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SettingActivity.this,"更新失败", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };
    //endregion

}
