package com.cateringpartner.cyhb.refund;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by AniChikage on 2017/6/10.
 */

public class RefundFragment extends Fragment {

    private View view;
    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private Util util;
    private RefundOrder[] rcvOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rcvorder, container, false);
        initIDandView();
        return view;
    }

    private void initIDandView(){
        util = new Util();
        util.setActivityFlag(1001);
        showData();
    }

    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result="";
            FetchData fetchData = new FetchData();
            //result = fetchData.Order_GetOrderByCompanyid(util.getGLOBALUSERID());
            if(util.getGLOBALUSERID().equals("6")){
                result = fetchData.Admin_getRefundOrder();
            }
            else{
                result = fetchData.Shop_getShopRefundOrderToday(util.getGLOBALUSERID());
            }
            Log.e("OrderByCompanyid", result);
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
                rcvOrders = new RefundOrder[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    String touser = temp.getJSONObject("user").getString("realname");
                    String touserphone = temp.getJSONObject("user").getString("phone");
                    String alipayaccount = temp.getJSONObject("user").getString("alipayaccount");
                    String totalprice = temp.getJSONObject("orders").getString("totalprice");
                    String rcvtime = temp.getJSONObject("orders").getString("createtime");
                    String rcv_goodname = temp.getJSONObject("allGoods").getString("goodname");
                    String rcv_goodimg = temp.getJSONObject("orders").getString("refundimg");
                    String refundreason = temp.getJSONObject("orders").getString("refundreason");
                    Integer goodid = temp.getJSONObject("allGoods").getInt("goodid");
                    //rcvOrders[i] = new RcvOrder(touser, touserphone,touseraddress,"¥"+totalprice, rcvtime, "http://47.92.74.241:8080/confimg/allgoods/" + goodid + ".jpg");
                    rcvOrders[i] = new RefundOrder(touser, touserphone,alipayaccount,"¥"+totalprice, rcvtime, rcv_goodimg, rcv_goodname, refundreason);
                }
                getData(true);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    public String TimeStamp2Date(String timestampString){
        Long timestamp = Long.parseLong(timestampString)*1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        return date;
    }
    //
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
                mAdapter.addAll(RefundOrderHolder.class, rcvOrders);
                if (mPage >= 0) {
                    //mAdapter.showNoMore();
                }
                if (isRefresh) {
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1000);
    }
}
