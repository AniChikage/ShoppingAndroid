package com.cateringpartner.cyhb.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.Window;
import android.view.WindowManager;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.main.MainActivity;
import com.cateringpartner.cyhb.main.MainActivityAdministrator;
import com.cateringpartner.cyhb.main.MainActivityShop;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by AniChikage on 2017/5/28.
 */

public class Welcome extends Activity {

    //region 变量和常量
    private static long showTime = 3000;
    Handler handler = new Handler();
    private Util util;
    private boolean isLogged = false;
    private String myusername="";
    private String mypassword="";
    private String TAG="this";
    //endregion

    //region
    /*
 * 获取当前程序的版本号
 */
    private String getVersionName() throws Exception{
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }

    /*
 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
 */
    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");//设置解析的数据源
        int type = parser.getEventType();
        UpdataInfo info = new UpdataInfo();//实体
        while(type != XmlPullParser.END_DOCUMENT ){
            switch (type) {
                case XmlPullParser.START_TAG:
                    if("version".equals(parser.getName())){
                        info.setVersion(parser.nextText()); //获取版本号
                    }else if ("url".equals(parser.getName())){
                        info.setUrl(parser.nextText()); //获取要升级的APK文件
                    }else if ("description".equals(parser.getName())){
                        info.setDescription(parser.nextText()); //获取该文件的信息
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
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

    //endregion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //
        util = new Util();
        try{
            //initData();
            try {
                File urlFile = new File("/sdcard/com.catering-partner/u.txt");
                InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String str = "";
                String mimeTypeLine = null ;
                while ((mimeTypeLine = br.readLine()) != null) {
                    str = str+mimeTypeLine;
                }
                //Toast.makeText(Welcome.this,str,Toast.LENGTH_SHORT).show();
                myusername = str;

                urlFile = new File("/sdcard/com.catering-partner/p.txt");
                isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
                br = new BufferedReader(isr);
                str = "";
                mimeTypeLine = null ;
                while ((mimeTypeLine = br.readLine()) != null) {
                    str = str+mimeTypeLine;
                }
                //Toast.makeText(Welcome.this,str,Toast.LENGTH_SHORT).show();
                mypassword = str;
                isLogged = true;
            } catch (Exception e) {
                e.printStackTrace();
                isLogged = false;
                //Toast.makeText(Welcome.this,"no",Toast.LENGTH_SHORT).show();
            }
            /*
            String extr = "sdcard";
            File mFolder = new File(extr + "/hhh");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
            Log.e("eeeeee",Environment.getExternalStorageDirectory().toString());
            */
        }
        catch (Exception e){
            Log.e("eeeeee",e.toString());
        }
        handler.postDelayed(new Runnable(){
            public void run(){
                init();
            }
        }, showTime);

    }

    //region 登陆入口
    private void goLogin(){
        new Thread(loginRunnable).start();
    }
    //endregion

    private Runnable loginRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.doLogin(myusername,mypassword);
            Log.e("login result",result);
            Message msg = new Message();
            msg.obj = result;
            loginHandler.sendMessage(msg);
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
                util.setUserphone(myusername);
                util.setGLOBALUSERID(uid);
                util.setUserphone(phone);
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
                //Toast.makeText(getApplication(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    private void init(){
        if(isLogged){
            new Thread(loginRunnable).start();
        }
        else{
            startActivity(new Intent(Welcome.this, LoginActivity.class));
            finish();
        }

    }

    //删除文件
    public static void delFile(){
        try{
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
        catch (Exception e){

        }
    }

    private void initData() {
        String filePath = "/sdcard/com.catering-partner/";
        String fileName = "log.txt";
        String username = "u.txt";
        String pwd = "p.txt";

        writeTxtToFile("txt content", filePath, fileName);
        writeTxtToFile("txt content", filePath, username);
        writeTxtToFile("txt content", filePath, pwd);
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
}
