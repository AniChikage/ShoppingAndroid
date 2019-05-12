package com.cateringpartner.cyhb.promotion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.specificCategory.GoodByCompany;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by AniChikage on 2017/6/10.
 */

public class PromotionDetail extends Activity {

    private String promotionCompanyID;
    private ImageView iv_banner;
    private TextView tv_company;
    private TextView tv_realname;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_cpmain;
    private TextView tv_cpcontent;
    private Util util;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.promotion_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏

        Bundle bundle = this.getIntent().getExtras();
        promotionCompanyID = bundle.getString("promotionCompanyId");
        Log.e("promotionCompanyID",promotionCompanyID);
        initData();
    }

    private void initData(){
        iv_banner = (ImageView)findViewById(R.id.banner);
        tv_company = (TextView)findViewById(R.id.company);
        tv_realname = (TextView)findViewById(R.id.realname);
        tv_phone = (TextView)findViewById(R.id.phone);
        tv_address = (TextView)findViewById(R.id.address);
        tv_cpmain = (TextView)findViewById(R.id.cpmain);
        tv_cpcontent = (TextView)findViewById(R.id.cpcontent);
        util = new Util();
        new Thread(getBannerRunnable).start();
    }

    Runnable getBannerRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_GetUserByUid(promotionCompanyID);
            Log.e("User_GetPromotion", result);
            Message msg = new Message();
            msg.obj = result;
            getBannerHandler.sendMessage(msg);
        }
    };

    Handler getBannerHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONArray arr = new JSONArray((String) msg.obj);
                final JSONObject jsonObject = (JSONObject)arr.get(0);
                tv_company.setText(jsonObject.getString("company"));
                final String companyid = String.valueOf(jsonObject.getInt("uid"));
                tv_company.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        util.setGLOBALCOMPANYID(promotionCompanyID);
                        Intent intent = new Intent();
                        intent.setClass(PromotionDetail.this, GoodByCompany.class);
                        startActivity(intent);
                    }
                });
                try{
                    Log.e("companylogoimg",jsonObject.getString("companylogoimg"));
                    Glide.with(getApplicationContext()).load(jsonObject.getString("companylogoimg")).into(iv_banner);
                    //Bitmap bitmap = getBitmap(jsonObject.getString("companylogoimg"));
                    //iv_banner.setImageBitmap(bitmap);
                    //iv_banner.setBackground(new BitmapDrawable(bitmap));
                }
                catch (Exception e){
                    Log.e("sss","sss");
                }

                tv_realname.setText(jsonObject.getString("realname"));
                tv_phone.setText(jsonObject.getString("phone"));
                tv_address.setText(jsonObject.getString("usercity")+jsonObject.getString("district")+jsonObject.getString("address"));
                tv_cpmain.setText("主营："+jsonObject.getString("cpmain"));
                tv_cpcontent.setText(jsonObject.getString("cpcontent"));
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    public static Bitmap getBitmap(String url) {
        URL imageURL = null;
        Bitmap bitmap = null;
        Log.e("inuni","URL = "+url);
        try {
            imageURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageURL
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
