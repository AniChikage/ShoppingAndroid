package com.cateringpartner.cyhb.newpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.domain.PayResult;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.SignUtils;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by AniChikage on 2017/6/8.
 */

public class Payment extends Activity{

    //region 初始化常量和变量
    //private String basketorderid;
    private String phone;
    private String global_totalprice="";
    private TextView tv_revperson;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_basketid;
    private TextView tv_totalprice;
    private TextView tv_pay;
    private Util util;
    private int curnum=0;
    private int isPaid=0;
    public static final String PARTNER = "2088821182273455";
    // 商户收款账号
    public static final String SELLER = "bpf65111@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQ" +
            "CKpwtfqukd/FQNNiNBPpdvw1MUAMFZgqThCt0AO7+UHne8q9sjm3nmZ2P0xKcdBOdqZi4ssaB0+gXjhWVb" +
            "seIR12Y7eoFXvSBvU8JB030drmot7IG4NDo2ZpH4Hmja0WqumJJgUMz5iDnDo4r5IUy4P6WPDKwWXKU10/g" +
            "+k5KzQ3XfO8tP6t0fOS2eZJnLzR2gqJHyDllhaNrl5cQJGSGF13qjKQ73KWur2m7qRW5HX2xnp6jyx8V5Yp" +
            "5t4tZeFaIwnO1IjJslmhkNTBNiFvkdDqVbFXLRVzMEdDt2qsLoVwZlReMLQorVWEoI9WoDzzNUndsnaQgSX/" +
            "J6VbdxuAZZAgMBAAECggEAOCllkAC4oG6+1QMi0KC70od5tVaA+vLSOl2eGyQYe7TDp2atQwUNIWirsk+dqUF" +
            "9QznUZkIkzn0fuYMlBTlOaw+m11zN224+Hxn8S5GQ0l79n1B3UtfE5JgzdB9NNaUx4zrlv6G7BTBKxZYPCs6U" +
            "NuWA+CqX4g7w1JbhrOc1uup1VqXuqn6vnTUQ+iQV+S87mF3atWbRa+tczEs/VzVfI8fIDE6a4Wu5w0bIfCqHM" +
            "/QNJ9+WQV+XAXSXB/IkVbwrMA0RNsG2+FI3+M6LltZjIlTkT4maGBn88sM/399aYxn/M+OwvNJw//G64KViUE" +
            "73Mft5ePegzpp9Ex5IEtJbAQKBgQDLa/WU5eCb19ZZPj13yurfbidJA4vViLuHr620HRKs8ySb3B1PN05B9/K" +
            "O22SrIF+ySoFAqI5gtsD7YRNJ/ijgOCjholT94Sq+3ggqNeC6n/+/OFH2dTU18KHIGPE+bCUjZe/rZQKf5EI4" +
            "ZZMzCwatQm8rlE47CRpxOx1uCxWA8QKBgQCufW0pAWS/9W2IQaFRDVna6Qabgfcx8g0x9r5/fpUU1tTu5pzkw" +
            "u+sU2gAvas1J5+Ruh87NNEaiGNB1kY4YTtEbXKMVe9FOHckOm14mU0Ku9t5D+bI+MiBOgJL1e4wRcMxsMoXC0" +
            "26qXcsIqN5PvAlAiD/Q/Bo4ArHOZgdG1Bb6QKBgBDnacLzz1Rj/TXxTB5WSGpEtxMELmySLjM6UA71k4W3tsU" +
            "U54Kbr+8fcZKZfKYTzJ/+PPtohlxXE3ZQPNAbRL5NLGiDmth2C51MyhTn4ULg5Nm5DmaSmtdepbMBD5sGVZe5" +
            "ctR20l8DEtWo6zCErAtdx+sxKccSHJZoOSvZ0/GhAoGAGziV/v+oqAmxvMAAWpSqn+wai8gKuE/6nwDPVzR4L" +
            "XgWX+66KzX+DfQ1FWiBfRlhW2qd1K64sFr2oR68da81f8dTR3FgknvsmkpDKb1At5v28Tv51QB6xy41jX8iWY" +
            "WIngfmfxHUXnpxjQEaX5/h9uQOIcSHjNTm2Y2ynVfVgYECgYEAgJqDWzKyclwhI6KhEV+Sp+AUcOTsa8iTDlp" +
            "sUAXEK9RdGhFo7dVAafJ1XrJ1iEm56lcj/7kn4WZKNgcW3NPjQz+9Ki3FMd5SlNw6I/kxZLeCyc2ivICHQRh6" +
            "wrwOmkrJCOTqjNx6Vu983gWMm23FIB3GeqbcTAqCiG4NUdSzO3g=";
    private static final int SDK_PAY_FLAG = 1;
    //endregion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.payment);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        //Bundle bundle = this.getIntent().getExtras();
        //basketorderid = bundle.getString("basketorderid");
        //Log.e("basketorderid",basketorderid);
        util = new Util();
        initIDandView();
    }

    //region 初始化
    private void initIDandView(){
        tv_revperson = (TextView) findViewById(R.id.revperson);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_address = (TextView) findViewById(R.id.address);
        tv_basketid = (TextView) findViewById(R.id.basketid);
        tv_totalprice = (TextView) findViewById(R.id.totalprice);
        tv_pay = (TextView) findViewById(R.id.pay);
        tv_pay.setOnClickListener(onClickListener);
        new Thread(initDataRunnable).start();
    }
    //endregion

    //region fetch data
    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_GetUserByUid(util.getGLOBALUSERID());
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
                tv_revperson.setText(jsonObject.getString("realname"));
                tv_phone.setText(phone);
                tv_address.setText(jsonObject.getString("usercity")+jsonObject.getString("district")+jsonObject.getString("address"));
                tv_totalprice.setText("总价："+util.getTotalprice()+"元");
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
                case R.id.pay:
                    //Toast.makeText(Payment.this, R.string.underDev, Toast.LENGTH_SHORT).show();
                    if(isPaid == 0){
                        doPay();
                    }else{
                        Toast.makeText(Payment.this, "您已支付成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    //endreigon

    //region
    private void doPay(){
        curnum = 0;
        testAlipay();
        //new Thread(doPayRunnable).start();
    }

    private Runnable doPayRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.setOrderPaid(util.getTopayorderids()[curnum]);
            Log.e("basketorderid result", result);
            Message msg = new Message();
            msg.obj = result;
            doPayHandler.sendMessage(msg);
        }
    };

    //
    Handler doPayHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if(curnum<util.getTopayorderids().length-1){
                    new Thread(doPayRunnable).start();
                    curnum+=1;
                }
                else{
                    Toast.makeText(getApplicationContext(),"支付完成",Toast.LENGTH_SHORT).show();
                    isPaid = 1;
                    tv_pay.setBackgroundColor(getResources().getColor(R.color.gray));
                }

            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion

    //region
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                       // Toast.makeText(Payment.this, "支付成功", Toast.LENGTH_SHORT).show();
                        new Thread(doPayRunnable).start();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(Payment.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(Payment.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    public void testAlipay() {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        String orderInfo = getOrderInfo("餐饮伙伴支付费用", "共"+util.getTopayorderids().length+"件商品", util.getTotalprice());

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(Payment.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
    //endregion
}
