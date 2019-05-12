package com.cateringpartner.cyhb.money;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.Util;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by AniChikage on 2017/6/10.
 */

public class MoneyFragment extends Fragment {

    private View view;

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
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

        view = inflater.inflate(R.layout.moneyfragment, container, false);
        initIDandView();
        util.setActivityFlag(1001);
        return view;
    }

    private void initIDandView(){
        tv_totalmoney = (TextView)view.findViewById(R.id.today_totalmoney);
        tv_thirdmoney = (TextView)view.findViewById(R.id.today_thirdmoney);
        tv_puremoney = (TextView)view.findViewById(R.id.today_puremoney);
        new Thread(getMoneyRunnable).start();
        util = new Util();
        showData();
    }

    private Runnable getMoneyRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.Admin_GetSales();
            Log.e("Admin_GetMoney",result);
            Message msg = new Message();
            msg.obj = result;
            getMoneyHandler.sendMessage(msg);
        }
    };

    Handler getMoneyHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                tv_totalmoney.setText(jsonObject.getString("totalMoney")+"元");
                tv_thirdmoney.setText(jsonObject.getString("thirdMoney")+"元");
                tv_puremoney.setText(jsonObject.getString("pureMoney")+"元");
            }
            catch (Exception ex){
                Log.e("getMoneyHandler",ex.toString());
            }
        }
    };

    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.Admin_GetMoneyDetail();
            Log.e("login result", result);
            Message msg = new Message();
            msg.obj = result;
            initDataHandler.sendMessage(msg);
        }
    };

    //
    Handler initDataHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONArray arr = new JSONArray((String) msg.obj);
                money = new Money[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    money[i] = new Money(temp.getString("companyname"),temp.getString("phone")+"@qq.com",temp.getString("realname"),
                            temp.getString("phone"),Double.valueOf(temp.getString("money"))+"");
                }
                getData(true);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    private void showData() {
        mAdapter = new MultiTypeAdapter(getActivity());
        mRecyclerView = (RefreshRecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                //getData(true);
                new Thread(initDataRunnable).start();
            }
        });
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                new Thread(initDataRunnable).start();
            }
        });
/*
        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });
*/
    }

    //
    public void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 0;
        } else {
            mPage++;
        }
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mAdapter.clear();
                    mRecyclerView.dismissSwipeRefresh();
                }
                mAdapter.addAll(MoneyHolder.class, money);
                if (mPage >= 0) {
                    mAdapter.showNoMore();
                }
                if (isRefresh) {
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1000);
    }

}
