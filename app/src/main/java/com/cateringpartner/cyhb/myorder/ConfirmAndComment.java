package com.cateringpartner.cyhb.myorder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.refund.Refund;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Decoder.BASE64Encoder;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by AniChikage on 2017/6/8.
 */

public class ConfirmAndComment extends Activity{

    //region 初始化常量和变量
    private String basketorderid;
    private String phone;
    private String goodid;
    private String isConfirmed;
    private String isCommented;
    private String imgPath="";
    private String alipayaccount="";
    private TextView tv_revperson;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_basketid;
    private TextView tv_totalprice;
    private TextView tv_confirm;
    private TextView tv_comment;
    private TextView tv_refund;
    private TextView tv_commentimg;
    private MaterialRatingBar materialRatingBar;
    private EditText et_comment;
    private Util util;
    //endregion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.confirmandcomment);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        Bundle bundle = this.getIntent().getExtras();
        basketorderid = bundle.getString("basketorderid");
        Log.e("basketorderid",basketorderid);

        initIDandView();
    }

    //region 初始化
    private void initIDandView(){
        tv_revperson = (TextView) findViewById(R.id.revperson);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_address = (TextView) findViewById(R.id.address);
        tv_basketid = (TextView) findViewById(R.id.basketid);
        tv_totalprice = (TextView) findViewById(R.id.totalprice);
        tv_confirm = (TextView) findViewById(R.id.confirm);
        tv_comment = (TextView) findViewById(R.id.comment);
        tv_refund = (TextView) findViewById(R.id.refund);
        tv_commentimg = (TextView) findViewById(R.id.commentimg);
        et_comment = (EditText) findViewById(R.id.commentcontent);
        materialRatingBar = (MaterialRatingBar) findViewById(R.id.ratingbar);
        tv_confirm.setOnClickListener(onClickListener);
        tv_comment.setOnClickListener(onClickListener);
        tv_refund.setOnClickListener(onClickListener);
        tv_commentimg.setOnClickListener(onClickListener);
        util = new Util();
        new Thread(initDataRunnable).start();
        materialRatingBar.setNumStars(5);
    }
    //endregion

    //region fetch data
    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getBasketByBasketId(basketorderid);
            Log.e("basketorderid result", result);
            Message msg = new Message();
            msg.obj = result;
            initDataHandler.sendMessage(msg);
        }
    };

    //
    Handler initDataHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONArray((String)msg.obj).get(0);
                phone = jsonObject.getString("phone");
                goodid = jsonObject.getString("goodid");
                isConfirmed = jsonObject.getString("rcvconfirm");
                isCommented = jsonObject.getString("iscomment");
                util.setGLOBALORDERID(jsonObject.getInt("oid")+"");
                if(isConfirmed.equals("1")){
                    tv_confirm.setBackgroundResource(R.color.gray);
                    tv_refund.setVisibility(View.VISIBLE);
                }
                else{
                    tv_refund.setVisibility(View.INVISIBLE);
                }
                if(isCommented.equals("1")){
                    tv_comment.setBackgroundResource(R.color.gray);
                }
                String totalprice = jsonObject.getString("totalprice");
                tv_phone.setText(phone);
                tv_basketid.setText("订单编号:"+basketorderid);
                tv_totalprice.setText("总价:¥"+totalprice);
                new Thread(getUserRunnable).start();
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    private Runnable getUserRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            //result = fetchData.getUserByPhone(phone);
            result = fetchData.User_GetUserByUid(util.getGLOBALUSERID());
            Log.e("getUserByUid result", result);
            Message msg = new Message();
            msg.obj = result;
            getUserHandler.sendMessage(msg);
        }
    };

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public String getImageStr(String imgFile) {

        byte[] data = null;

        // 加密
        try {
            InputStream inputStream = null;
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    //
    Handler getUserHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONArray((String)msg.obj).get(0);
                String realname = jsonObject.getString("realname");
                String address = jsonObject.getString("address");
                alipayaccount = jsonObject.getString("alipayaccount");
                tv_revperson.setText("收货人:"+realname);
                tv_address.setText("收货地址:"+address);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion

    //region onclicklisener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.confirm:
                    if(isConfirmed.equals("1")){
                        Toast.makeText(ConfirmAndComment.this, R.string.hasrcved, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        new Thread(confirmOrderRunnable).start();
                    }
                    break;
                case R.id.comment:
                    //Toast.makeText(ConfirmAndComment.this, R.string.underDev, Toast.LENGTH_SHORT).show();
                    if(isCommented.equals("1")){
                        Toast.makeText(ConfirmAndComment.this, R.string.hascommented, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(!et_comment.getText().toString().trim().equals("")){
                            new Thread(addCommentRunnable).start();
                        }
                        else{
                            Toast.makeText(ConfirmAndComment.this, R.string.pleaseinputcomment, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.refund:
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("alipayaccount",alipayaccount);
                    intent.putExtras(bundle);
                    intent.setClass(ConfirmAndComment.this, Refund.class);
                    startActivity(intent);
                    break;
                case R.id.commentimg:
                    uploadImg();
                    break;
                default:
                    break;
            }
        }
    };

    private void uploadImg(){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i,1);
    }
    //endreigon

    //region 确认收货
    private Runnable confirmOrderRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.confirmOrder(basketorderid);
            Log.e("getUserByPhone result", result);
            Message msg = new Message();
            msg.obj = result;
            confirmOrderHandler.sendMessage(msg);
        }
    };

    //
    Handler confirmOrderHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    Toast.makeText(ConfirmAndComment.this, R.string.hasrcvedsuccess, Toast.LENGTH_SHORT).show();
                    tv_confirm.setBackgroundResource(R.color.gray);
                    isConfirmed = "1";
                    tv_refund.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(ConfirmAndComment.this, R.string.hasrcvedfailed, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion

    //region 确认提交评价
    private Runnable commentRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.commentOrder(basketorderid);
            Log.e("getUserByPhone result", result);
            Message msg = new Message();
            msg.obj = result;
            commentHandler.sendMessage(msg);
        }
    };

    //
    Handler commentHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    Toast.makeText(ConfirmAndComment.this, R.string.commentsuccess, Toast.LENGTH_SHORT).show();
                    tv_comment.setBackgroundResource(R.color.gray);
                    isCommented = "1";
                }
                else{
                    Toast.makeText(ConfirmAndComment.this, R.string.commentfailed, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion

    //region 添加评论
    private Runnable addCommentRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                if(!imgPath.equals("")){
                    String result;
                    FetchData fetchData = new FetchData();
                    result = fetchData.User_AddComment(goodid, util.getGLOBALUSERID(),
                            materialRatingBar.getRating()+"",
                            et_comment.getText().toString().trim(),getImageStr(imgPath));
                    Log.e("addCoable result", result);
                    Message msg = new Message();
                    msg.obj = result;
                    addCommentHandler.sendMessage(msg);
                }

            }
            catch (Exception e){

            }

        }
    };

    //
    Handler addCommentHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    new Thread(commentRunnable).start();
                }
                else{
                    Toast.makeText(ConfirmAndComment.this, R.string.hasrcvedfailed, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

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
            imgPath = picturePath;
            //Toast.makeText(getApplicationContext(),imgPath+","+util.getGLOBALUSERID()+","+materialRatingBar.getNumStars()+","+et_comment.getText().toString().trim().toString(),Toast.LENGTH_SHORT).show();
            cursor.close();

            Luban.with(this)
                    .load(imgPath)                                   // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件

                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                        }
                    }).launch();    //启动压缩

        }
    }
    //endregion

    //region 删除订单
    private Runnable delBasketOrderRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.delBasketOrder(basketorderid);
            Log.e("getUserByPhone result", result);
            Message msg = new Message();
            msg.obj = result;
            delBasketOrderHandler.sendMessage(msg);
        }
    };

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    //
    Handler delBasketOrderHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String status = jsonObject.getString("status");
                if(status.equals("1")){
                    Toast.makeText(ConfirmAndComment.this, R.string.delordersuccess, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ConfirmAndComment.this, R.string.delorderfailed, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion
}
