package com.cateringpartner.cyhb.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AniChikage on 2017/9/21.
 */

public class CheckVersion extends Activity {

    private String currentVersion="";
    private String webVersion="";
    private TextView chenkhint;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        }
        catch(Exception e){
            Log.e("checkversion",e.toString());
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.checkversion);
        try{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //
        }
        catch(Exception e){
            Log.e("checkversion",e.toString());
        }
        chenkhint = (TextView)findViewById(R.id.checkhint);
        chenkhint.setVisibility(View.INVISIBLE);
        if(isNetworkConnected(getApplicationContext())){
            try{
                currentVersion = getCurrentVersion();
                Log.e("currentVersion",currentVersion);
                new Thread(getWebVersionRunnable).start();
            }
            catch (Exception ex){

            }
        }else{
            chenkhint.setVisibility(View.VISIBLE);
        }
    }

    public boolean isNetworkConnected(Context context) {
             if (context != null) {
                     ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                             .getSystemService(Context.CONNECTIVITY_SERVICE);
                     NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                     if (mNetworkInfo != null) {
                             return mNetworkInfo.isAvailable();
                         }
                 }
             return false;
    }

    public String getCurrentVersion() {
        String version = "1.0";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            Log.e("denied?",e.toString());
        }
        return version;
    }

    Runnable getWebVersionRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                FetchData fetchData = new FetchData();
                String strings = fetchData.getVersion();
                Message msg = new Message();
                msg.obj = strings;
                getWebVersionHandler.sendMessage(msg);
            }
            catch (Exception e){
                Log.e("ddd",e.toString());
            }
        }
    };

    Handler getWebVersionHandler = new Handler(){
        public void handleMessage(Message msg) {
            try{
                JSONArray jsonArray = new JSONArray((String)msg.obj);
                JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                webVersion = jsonObject.getString("version");
                Log.e("webversion",webVersion);
                if(!webVersion.equals(currentVersion)){
                    showUpdataDialog();
                }
                else{
                    Intent intent = new Intent(CheckVersion.this,Welcome.class);
                    startActivity(intent);
                    finish();
                }
            }
            catch (Exception e){
                Log.e("reset err",e.toString());
            }
        }
    };

    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this) ;
        builer.setTitle("有新版可以更新");
        builer.setMessage("更新?");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 积极

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                downLoadApk();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {// 积极

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(CheckVersion.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        });
        //("确定", (dialog, which) -> downLoadApk());
        //当点取消按钮时不做任何举动
        //builer.setNegativeButton("取消", (dialogInterface, i) -> {});
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    protected void downLoadApk() {
        //进度条
        final ProgressDialog pd;
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer("http://47.92.74.241:80/catering_dev2.apk", pd);
                    //安装APK
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                }
            }}.start();
    }

    @Nullable
    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }

    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }


}
