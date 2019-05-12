package com.cateringpartner.cyhb.refund;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Decoder.BASE64Encoder;

/**
 * Created by AniChikage on 2017/6/14.
 */

public class Refund extends Activity {

    private EditText et_refundaccount;
    private EditText et_refundreason;
    private TextView tv_upload;
    private TextView tv_chooseImage;
    private Util util;
    private String imgstr="";
    private String alipayaccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.refund);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏

        initData();
    }

    private void initData(){
        Bundle bundle = getIntent().getExtras();
        alipayaccount = bundle.getString("alipayaccount");//读出数据
        et_refundaccount = (EditText) findViewById(R.id.refundaccount);
        et_refundaccount.setText(alipayaccount);
        et_refundreason = (EditText)findViewById(R.id.refundreason);
        tv_upload = (TextView)findViewById(R.id.upload);
        tv_chooseImage = (TextView)findViewById(R.id.chooseImage);
        util = new Util();
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(refundRunnable).start();
            }
        });
        tv_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            imgstr = picturePath;
            cursor.close();
        }
    }

    private Runnable refundRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            if(!imgstr.equals("")){
                try{
                    Log.e("sdfdf",imgstr);
                    Log.e("sdfdf",bitmapToBase64(BitmapFactory.decodeFile(imgstr)));
                    //Log.e("sdfdf",getImageStr(imgstr));
                    result = fetchData.Order_refundOrder("1",et_refundaccount.getText().toString().trim(),
                            et_refundreason.getText().toString().trim(),
                            Integer.valueOf(util.getGLOBALORDERID()));
                    Log.e("getUserByPhone result", result);
                    Message msg = new Message();
                    msg.obj = result;
                    refundHandler.sendMessage(msg);
                }
                catch (Exception e){
                    Log.e("upload err",e.toString());
                }
                try{
                    fetchData.Order_refundOrderImg(getImageStr(imgstr),
                            Integer.valueOf(util.getGLOBALORDERID()));
                }
                catch (Exception e){

                }
            }
        }
    };

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }



    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    //
    Handler refundHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {

                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    Toast.makeText(Refund.this, "提交成功，请等待审核", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Refund.this, "提交失败", Toast.LENGTH_SHORT).show();
                }
                /*
                String status = (String)msg.obj;
                if(status.contains("1")){
                    Toast.makeText(Refund.this, "提交成功，请等待审核", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Refund.this, "提交失败", Toast.LENGTH_SHORT).show();
                }
                */
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };


}
