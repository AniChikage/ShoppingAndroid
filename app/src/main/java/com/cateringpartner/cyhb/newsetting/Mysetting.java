package com.cateringpartner.cyhb.newsetting;

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
import com.cateringpartner.cyhb.setting.ResetPwd;
import com.cateringpartner.cyhb.util.Util;
import com.cateringpartner.cyhb.widget.LoginActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import info.hoang8f.widget.FButton;

/**
 * Created by AniChikage on 2017/10/8.
 */

public class Mysetting extends Activity {

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
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        } catch (Exception e) {
            Log.e("checkversion", e.toString());
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.mysetting);
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //
        } catch (Exception e) {
            Log.e("checkversion", e.toString());
        }

        util = new Util();
        initIDandView();
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
        s_usercity.setItems("沈阳市", "本溪市", "大连市", "鞍山市", "抚顺市", "丹东市","锦州市","营口市","阜新市","辽阳市","盘锦市","铁岭市","朝阳市","葫芦岛市");
        s_usercity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Toast.makeText(getApplicationContext(), "Clicked " + item, Toast.LENGTH_SHORT).show();
                switch (item){
                    case "沈阳市":
                        s_district.setItems("和平区", "沈河区", "大东区", "皇姑区", "铁西区","苏家屯区","浑南区","沈北新区","于洪区","辽中区");
                        break;
                    case "本溪市":
                        s_district.setItems("平山区", "溪湖区", "明山区", "南芬区");
                        break;
                    case "大连市":
                        s_district.setItems("中山区", "西岗区", "沙河口区", "甘井子区", "旅顺口区","金州区","普兰店区");
                        break;
                    case "鞍山市":
                        s_district.setItems("铁东区", "铁西区", "立山区", "千山区");
                        break;
                    case "抚顺市":
                        s_district.setItems("新抚区", "东洲区", "望花区", "顺城区");
                        break;
                    case "丹东市":
                        s_district.setItems("元宝区", "振兴区", "振安区");
                        break;
                    case "锦州市":
                        s_district.setItems("古塔区", "凌河区", "太和区");
                        break;
                    case "营口市":
                        s_district.setItems("站前区", "西市区", "鲅鱼圈区", "老边区");
                        break;
                    case "阜新市":
                        s_district.setItems("海州区", "新邱区", "太平区", "清河门区", "细河区");
                        break;
                    case "辽阳市":
                        s_district.setItems("白塔区", "文圣区", "宏伟区", "弓长岭区", "太子河区");
                        break;
                    case "盘锦市":
                        s_district.setItems("双台子区", "兴隆台区", "大洼区");
                        break;
                    case "铁岭市":
                        s_district.setItems("银州区", "清河区");
                        break;
                    case "朝阳市":
                        s_district.setItems("双塔区", "龙城区");
                        break;
                    case "葫芦岛市":
                        s_district.setItems("连山区", "龙港区", "南票区");
                        break;
                }
            }
        });
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
        Toast.makeText(Mysetting.this,"注销成功，请返回重新登陆",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Mysetting.this,"更新成功", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Mysetting.this,"更新失败", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };
    //endregion
}
