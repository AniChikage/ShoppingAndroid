package com.cateringpartner.cyhb.promotion;

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
 * Created by AniChikage on 2017/6/2.
 */

public class PromotionFragment extends Fragment {

    private View view;
    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private Promotions[] allPromotions;
    private Integer[] promotionID;
    private Util util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_promotion, container, false);
        initIDandView();
        return view;
    }

    private void initIDandView() {
        util = new Util();
        util.setActivityFlag(3);
        //new Thread(initDataRunnable).start();
        new Thread(getBannerRunnable).start();
    }

    /*
    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getPromotionsByPage("1");
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
                allPromotions = new Promotions[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    int id = temp.getInt("pid");
                    String pcompany = temp.getString("pcompany");
                    String pmain = temp.getString("pmain");
                    //allPromotions[i] = new Promotions(pcompany, pmain, "http://47.92.74.241:8080/confimg/promotion/" + id + ".jpg");
                }
                showData();
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
*/
    Runnable getBannerRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.User_GetPromotion("1");
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
                allPromotions = new Promotions[arr.length()];
                promotionID = new Integer[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    int id = temp.getInt("uid");
                    promotionID[i] = id;
                    String pcompany = temp.getString("company");
                    String pmain = temp.getString("cpmain");
                    String pimg = temp.getString("companylogoimg");
                    allPromotions[i] = new Promotions(String.valueOf(id),pcompany, pmain, pimg);
                }
                util.setGlobalPromotionId(promotionID);
                showData();
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    //
    private void showData() {
        mAdapter = new MultiTypeAdapter(getActivity());
        mRecyclerView = (RefreshRecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });
/*
        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
            }
        });
*/
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

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
                mAdapter.addAll(PromotionHolder.class, allPromotions);
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
