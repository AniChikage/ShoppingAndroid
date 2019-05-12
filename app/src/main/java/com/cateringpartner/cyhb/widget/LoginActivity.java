package com.cateringpartner.cyhb.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
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
import com.cateringpartner.cyhb.application.ApplicationUtil;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.main.MainActivity;
import com.cateringpartner.cyhb.main.MainActivityAdministrator;
import com.cateringpartner.cyhb.main.MainActivityShop;
import com.cateringpartner.cyhb.newregister.Myregister;
import com.cateringpartner.cyhb.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by AniChikage on 2017/5/24.
 */

public class LoginActivity extends Activity {

    //region 变量和常量
    private TextView login_tv;
    private TextView register_tv;
    private TextView forgetpwd_tv;
    private EditText phone_et;
    private EditText password_et;
    private Util util;
    private ApplicationUtil applicationUtil;
    //endregion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.login);
        mainEntry();
    }

    //region 主程序
    private void mainEntry(){
        setTitleBarTransparent();
        initID();
    }
    //endregion

    //region 透明状态栏
    private void setTitleBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    //endregion

    //region 初始化ID
    private void initID(){
        applicationUtil = new ApplicationUtil();
        login_tv = (TextView)findViewById(R.id.login);
        register_tv = (TextView)findViewById(R.id.register_tv);
        forgetpwd_tv = (TextView)findViewById(R.id.forgetpwd_tv);
        login_tv.setOnClickListener(onClickListener);
        register_tv.setOnClickListener(onClickListener);
        forgetpwd_tv.setOnClickListener(onClickListener);
        phone_et = (EditText)findViewById(R.id.phone);
        password_et = (EditText)findViewById(R.id.password);
        //phone_et.setText("hzs");
        //password_et.setText("123456");
        util = new Util();
    }
    //endregion

    //region CLICK事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login:
                    goLogin();
                    break;
                case R.id.register_tv:
                    goRegister();
                    break;
                case R.id.forgetpwd_tv:
                    goForgetpwd();
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    //region 登陆入口
    private void goLogin(){
        new Thread(loginRunnable).start();
    }
    //endregion

    //region 注册入口
    private void goRegister(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, Myregister.class);
        startActivity(intent);
    }
    //endregion

    //region 忘记密码入口
    private void goForgetpwd(){
        //Toast.makeText(getApplicationContext(), R.string.underDev, Toast.LENGTH_SHORT);
        if(!phone_et.getText().toString().trim().equals("")){
            new Thread(resetPwdRunnable).start();
        }
        else{
            Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT);
        }
    }
    //endregion

    private Runnable loginRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.doLogin(phone_et.getText().toString(),password_et.getText().toString());
            Log.e("login result",result);
            Message msg = new Message();
            msg.obj = result;
            loginHandler.sendMessage(msg);
        }
    };

    private Runnable getUserRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getUserByPhone(phone_et.getText().toString());
            Log.e("login result",result);
            Message msg = new Message();
            msg.obj = result;
            getUserHandler.sendMessage(msg);
        }
    };

    private Runnable resetPwdRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_ResetPawword(phone_et.getText().toString().trim());
            Log.e("reset result",result);
            Message msg = new Message();
            msg.obj = result;
            resetPwdHandler.sendMessage(msg);
        }
    };

    Handler getUserHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONArray jsonArray = new JSONArray((String)msg.obj);
                JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                String usertype = jsonObject.getString("usertype");
                String uid = jsonObject.getString("uid");
                util.setUserphone(phone_et.getText().toString().trim());
                util.setGLOBALUSERID(uid);
                //util.setUid(jsonObject.getString("uid"));
                if(usertype.equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(usertype.equals("2")){
                    String company = jsonObject.getString("company");
                    util.setGLOBALCOMPANY(company);
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivityShop.class);
                    startActivity(intent);
                    finish();
                }
                else if(usertype.equals("0")){
                    String company = jsonObject.getString("company");
                    util.setGLOBALCOMPANY(company);
                    //util.setGLOBALCOMPANYID(uid);
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivityAdministrator.class);
                    startActivity(intent);
                    finish();
                }
            }
            catch (Exception ex){
                Log.e("sfsd",ex.toString());
            }
        }
    };

    Handler checkPhoneHandler = new Handler() {
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            Status status = new Status();
            status = gson.fromJson((String)msg.obj, Status.class);
            Log.e("check result",status.getStatus());
            if(status.getStatus().trim().equals("true")){
                new Thread(loginRunnable).start();
            }
            else{
                Toast.makeText(getApplication(), "用户名不存在", Toast.LENGTH_SHORT).show();
            }
        }
    };

    Handler resetPwdHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                if(jsonObject.getString("status").equals("1")){
                    Toast.makeText(getApplication(), "您的密码已经重置为您的手机号", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplication(), "用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.e("reset err",e.toString());
            }
        }
    };

    Handler loginHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONArray jsonArray = new JSONArray((String)msg.obj);
                JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                String usertype = jsonObject.getString("usertype");
                String uid = jsonObject.getString("uid");
                String phone = jsonObject.getString("phone");
                util.setUserphone(phone_et.getText().toString().trim());
                util.setGLOBALUSERID(uid);
                util.setUserphone(phone);
                applicationUtil.setUid(uid);
                try{
                    delFile();
                    initData(phone_et.getText().toString().trim(),password_et.getText().toString().trim());
                }
                catch (Exception e){

                }
                if(usertype.equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(usertype.equals("2")){
                    String company = jsonObject.getString("company");
                    util.setGLOBALCOMPANY(company);
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivityShop.class);
                    startActivity(intent);
                    finish();
                }
                else if(usertype.equals("0")){
                    String company = jsonObject.getString("company");
                    util.setGLOBALCOMPANY(company);
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivityAdministrator.class);
                    startActivity(intent);
                    finish();
                }
            }
            catch (Exception ex){
                Log.e("sfsd",ex.toString());
                Toast.makeText(getApplication(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    };
    //endregion

    //region
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

    private void initData(String uu, String pp) {
        String filePath = "/sdcard/com.catering-partner/";
        String fileName = "log.txt";
        String username = "u.txt";
        String pwd = "p.txt";

        writeTxtToFile("txt content", filePath, fileName);
        writeTxtToFile(uu, filePath, username);
        writeTxtToFile(pp, filePath, pwd);
    }

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }
    //endregion
}
