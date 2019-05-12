package com.cateringpartner.cyhb.register;

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
import com.cateringpartner.cyhb.widget.Status;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * Created by AniChikage on 2017/6/10.
 */

public class RegisterShopActivity extends Activity{

    private EditText et_phone;
    private EditText et_password;
    private EditText et_username;
    private EditText et_realname;
    private EditText et_address;
    private EditText et_companyname;
    private EditText et_cpmain;
    private EditText et_cpcontent;
    private MaterialSpinner s_usercity;
    private MaterialSpinner s_district;
    private TextView tv_register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.registershop);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏

        initIDandView();
    }

    private void initIDandView(){
        et_phone = (EditText) findViewById(R.id.phone);
        et_password = (EditText) findViewById(R.id.password);
        et_username = (EditText) findViewById(R.id.username);
        et_realname = (EditText) findViewById(R.id.realname);
        et_address = (EditText) findViewById(R.id.address);
        et_companyname = (EditText) findViewById(R.id.companyname);
        et_cpmain = (EditText) findViewById(R.id.cpmain);
        et_cpcontent = (EditText) findViewById(R.id.cpcontent);
        tv_register = (TextView) findViewById(R.id.register);
        s_usercity = (MaterialSpinner) findViewById(R.id.usercity);
        s_district = (MaterialSpinner) findViewById(R.id.district);
        tv_register.setOnClickListener(onClickListener);
        s_usercity.setItems("沈阳市","本溪市",  "大连市", "鞍山市", "抚顺市", "丹东市","锦州市","营口市","阜新市","辽阳市","盘锦市","铁岭市","朝阳市","葫芦岛市");
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
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.register:
                    goRegister();
                    break;
                default:
                    break;
            }
        }
    };

    private void goRegister(){
        if(et_phone.getText().toString().trim().equals("")||et_password.getText().toString().trim().equals("")||et_username.getText().toString().trim().equals("")
                ||et_realname.getText().toString().trim().equals("")||et_address.getText().toString().trim().equals("")||
                et_companyname.getText().toString().trim().equals("")||et_cpmain.getText().toString().trim().equals("")||et_cpcontent.getText().toString().trim().equals("")){
            Toast.makeText(RegisterShopActivity.this,"您需要填完所有信息！", Toast.LENGTH_SHORT).show();
        }
        else{
            new Thread(registerRunnable).start();
        }
    }

    //region 登陆线程
    private Runnable registerRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_addUserShopper(et_username.getText().toString(),et_phone.getText().toString(),et_password.getText().toString(),"2",et_realname.getText().toString(),
                    s_usercity.getText().toString(),s_district.getText().toString(),et_address.getText().toString(),et_companyname.getText().toString(),
                    et_cpmain.getText().toString(),et_cpcontent.getText().toString());
            Log.e("login result",result);
            Message msg = new Message();
            msg.obj = result;
            registerHandler.sendMessage(msg);
        }
    };

    Handler registerHandler = new Handler() {
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            Status status = new Status();
            status = gson.fromJson((String)msg.obj, Status.class);
            Log.e("check result",status.getStatus());
            if(status.getStatus().trim().equals("1")){
                Toast.makeText(getApplication(), "您注册成功，请返回登陆", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplication(), "您注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
