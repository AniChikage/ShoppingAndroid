package com.cateringpartner.cyhb.specificCategory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class GoodByCompany extends Activity {

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private String goodtype;
    public Util util;
    private AllGoods[] allGoodses;
    private String[] allGoodsID;
    private String COMPANYNAMEID;
    private EditText et_searchname;
    private TextView tv_goback;
    private TextView tv_search;
    public static GoodByCompany test_a = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.goodbycompany);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        util = new Util();
        //activityflag = 555
        util.setActivityFlag(555);
        util.setPreGoodDetailActivityFlag(555);
        //test_a = this;

        COMPANYNAMEID = util.getGLOBALCOMPANYID();
        Log.e("COMPANYNAMEID",COMPANYNAMEID);
        initData();

    }

    private void initData(){
        et_searchname = (EditText)findViewById(R.id.search_et);
        tv_goback = (TextView)findViewById(R.id.goback);
        tv_search = (TextView)findViewById(R.id.gosearch);
        //et_searchname.setOnClickListener(onClickListener);
        tv_goback.setOnClickListener(onClickListener);
        tv_search.setOnClickListener(onClickListener);
        new Thread(initDataRunnable).start();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.goback:
                    goBack();
                    break;
                case R.id.gosearch:
                    goSearch();
                    break;
            }
        }
    };

    private void goSearch(){
        new Thread(initDataRunnable).start();
    }

    private void goBack(){
        finish();
    }

    private Runnable initDataRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            //result = fetchData.Good_getGoodByCompany(COMPANYNAME);
            result = fetchData.Good_getGoodByCompanyidAndSearchname(COMPANYNAMEID,et_searchname.getText().toString().trim());
            Log.e("login result",result);
            Message msg = new Message();
            msg.obj = result;
            initDataHandler.sendMessage(msg);
        }
    };

    Handler initDataHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONArray arr = new JSONArray((String)msg.obj);
                allGoodses = new AllGoods[arr.length()];
                allGoodsID = new String[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    int id = temp.getJSONObject("allGoods").getInt("goodid");
                    String goodimg = temp.getJSONObject("allGoods").getString("img");
                    String goodname = temp.getJSONObject("allGoods").getString("goodname");
                    String goodintroduction = temp.getJSONObject("allGoods").getString("goodintroduction");
                    String goodprice = temp.getJSONObject("allGoods").getString("goodprice");
                    String goodcommet = temp.getJSONObject("allGoods").getString("averank");
                    String goodordernum = temp.getJSONObject("allGoods").getString("orderCount");
                    allGoodsID[i] = String.valueOf(id);
                    allGoodses[i] = new AllGoods(goodimg,goodname,goodintroduction,goodprice,goodcommet,goodordernum);
                }
                util.setGoodsGroupShop(allGoodsID);
                showData();
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };

    private void showData(){
        mAdapter = new MultiTypeAdapter(this);
        mRecyclerView = (RefreshRecyclerView)findViewById(R.id.recycle_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845,0xFFE44F98,0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
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
                mAdapter.addAll(SpecificGoodsHolder.class, allGoodses);
                if (mPage >= 0) {
                    //mAdapter.showNoMore();
                }
                if(isRefresh){
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1000);
    }

    /**
     * 调用finish方法，或者系统回收资源时调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //util.setActivityFlag(6);
    }

    public void goFinish(){

    }
}
