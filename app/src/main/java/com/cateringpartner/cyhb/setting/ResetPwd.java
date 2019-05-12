package com.cateringpartner.cyhb.setting;

import android.app.Activity;
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

import org.json.JSONObject;

/**
 * Created by AniChikage on 2017/9/10.
 */

public class ResetPwd extends Activity {

    //region
    private EditText et_prepwd;
    private EditText et_newpwd;
    private EditText et_repeatnewpwd;
    private TextView tv_resetpwd;
    private Util util;
    //endregion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.resetpwd);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        initData();
    }

    //region 初始化
    private void initData(){
        util = new Util();
        et_prepwd = (EditText) findViewById(R.id.prepwd);
        et_newpwd = (EditText) findViewById(R.id.newpwd);
        et_repeatnewpwd = (EditText) findViewById(R.id.repeatnewpwd);
        tv_resetpwd = (TextView) findViewById(R.id.resetpassword);
        tv_resetpwd.setOnClickListener(onClickListener);
    }
    //endregion

    //region click事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.resetpassword:
                    resetPwd();
                    break;
            }
        }
    };
    //endregion

    //region 重置密码
    private void resetPwd(){
        new Thread(checkPassword).start();
    }
    //endregion

    //region 校验当前密码是否正确
    private Runnable checkPassword = new Runnable() {
        @Override
        public void run() {
            if(et_newpwd.getText().toString().trim().equals(et_repeatnewpwd.getText().toString().trim())){
                FetchData fetchData = new FetchData();
                String result = fetchData.User_ResetPawwordByUid(util.getGLOBALUSERID(),et_prepwd.getText().toString().trim(),et_newpwd.getText().toString().trim());
                Message msg = new Message();
                msg.obj = result;
                resetPawwordHandler.sendMessage(msg);
            }else{
                Toast.makeText(getApplicationContext(),"重复密码错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler resetPawwordHandler = new Handler(){
        public void handleMessage(Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    Toast.makeText(ResetPwd.this,"重置完成",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPwd.this,"重置失败",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex){
                Log.e("重置密码",ex.toString());
            }
        }
    };
    //endregion

}
