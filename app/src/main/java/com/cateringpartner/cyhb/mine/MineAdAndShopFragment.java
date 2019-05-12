package com.cateringpartner.cyhb.mine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.basket.BasketHolder;
import com.cateringpartner.cyhb.basket.Baskets;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.setting.SettingActivity;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by AniChikage on 2017/6/2.
 */


public class MineAdAndShopFragment extends Fragment{

    private TextView tv_realname;
    private LinearLayout ll_setting;
    private Util util;
    private View view;
    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private Baskets[] allBaskets;
    private Integer[] basketOrderGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        util = new Util();
        initIDandView();
        return view;
    }

    //region 初始化ID
    private void initIDandView(){
        tv_realname = (TextView) view.findViewById(R.id.realname);
        ll_setting = (LinearLayout) view.findViewById(R.id.setting);
        ll_setting.setOnClickListener(onClickListener);
        new Thread(getUserRealnameRunable).start();
        //showData();
    }
    //endregion

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

    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_getOrder(util.getGLOBALUSERID(),"1");
            Log.e("login result", result);
            Message msg = new Message();
            msg.obj = result;
            initDataHandler.sendMessage(msg);
        }
    };

    Handler initDataHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONArray arr = new JSONArray((String) msg.obj);
                allBaskets = new Baskets[arr.length()];
                basketOrderGroup = new Integer[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    String goodid = temp.getJSONObject("allGoods").getString("goodid");
                    String company = temp.getJSONObject("allGoods").getString("goodcompany");
                    String goodname = temp.getJSONObject("allGoods").getString("goodname");
                    String totalprice = temp.getJSONObject("orders").getString("totalprice");
                    String goodimg = temp.getJSONObject("allGoods").getString("img");
                    String createtime = temp.getJSONObject("orders").getString("createtime");
                    createtime = TimeStamp2Date(createtime,"yyyy-MM-dd HH:mm:ss");
                    allBaskets[i] = new Baskets(company, goodname,"¥"+totalprice, goodimg,"商品数量："+temp.getJSONObject("orders").getString("goodnum"),createtime);
                    basketOrderGroup[i] = temp.getJSONObject("orders").getInt("oid");
                }
                getData(true);
                util.setBasketOrderGroup(basketOrderGroup);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

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
                mAdapter.addAll(BasketHolder.class, allBaskets);
                if (mPage >= 0) {
                    //mAdapter.showNoMore();
                }
                if (isRefresh) {
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1000);
    }

    //region clicklisnter
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SettingActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    //region 获取用户名
    private Runnable getUserRealnameRunable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getUserByPhone(util.getUserphone());
            Log.e("getUserByPhone result",result);
            Message msg = new Message();
            msg.obj = result;
            getUserRealnameHandler.sendMessage(msg);
        }
    };

    private Handler getUserRealnameHandler = new Handler(){
        public void handleMessage(Message msg) {
            try{
                JSONArray arr = new JSONArray((String)msg.obj);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    tv_realname.setText(temp.getString("realname"));
                }
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };

    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString);
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
    //endregion
}
