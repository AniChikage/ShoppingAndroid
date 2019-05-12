package com.cateringpartner.cyhb.money;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.Util;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONObject;

import java.util.Random;

/**
 * Created by AniChikage on 2017/6/10.
 */

public class ShopMoneyFragment extends Fragment {

    private View view;

    private BarChart mBarChart;
    private int mPage = 0;
    private TextView tv_totalmoney;
    private TextView tv_thirdmoney;
    private TextView tv_deliverymoney;
    private TextView tv_puremoney;
    private Util util;
    private Money[] money;

    private Random random;//用于产生随机数字
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.shopmoneyfragment, container, false);
        initIDandView();

        return view;
    }

    private void initIDandView(){
        tv_totalmoney = (TextView)view.findViewById(R.id.today_totalmoney);
        tv_thirdmoney = (TextView)view.findViewById(R.id.today_thirdmoney);
        tv_puremoney = (TextView)view.findViewById(R.id.today_puremoney);
        new Thread(getMoneyRunnable).start();
        util = new Util();
    }

    private Runnable getMoneyRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.shopGetShopMoney(util.getGLOBALUSERID()+"");
            Log.e("Admin_GetMoney",result);
            Log.e("shopid",util.getGLOBALUSERID());
            Message msg = new Message();
            msg.obj = result;
            getMoneyHandler.sendMessage(msg);
        }
    };

    Handler getMoneyHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                Double allmoney = Double.valueOf(jsonObject.getString("puremoney"));
                tv_totalmoney.setText(String.format("%.2f",allmoney)+"元");
                tv_thirdmoney.setText("未计入运费，转账时运费由管理员按照订单重量扣除，如有异议，请与管理员联系");
                //tv_puremoney.setText(String.format("%.2f",allmoney*0.99)+"元");
            }
            catch (Exception ex){
                Log.e("getMoneyHandler",ex.toString());
            }
        }
    };


}
