package com.cateringpartner.cyhb.specificCategory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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

public class SearchGood extends Activity {

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private String goodtype;
    public Util util;
    private AllGoods[] allGoodses;
    private String[] allGoodsID;
    private String SEARCHEDGOODNAME;
    private TextView tv_gosearch;
    private TextView tv_goback;
    private EditText search_et;
    private TextView tv_zonghe;
    private TextView tv_price;
    private TextView tv_sales;

    private String sortFlag="zonghe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.categorydetail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        util = new Util();
        util.setActivityFlag(6);

        SEARCHEDGOODNAME = util.getSEARCHEDGOODNAME();
        initData();

    }

    private void initData(){
        tv_zonghe = (TextView)findViewById(R.id.zonghe);
        tv_price = (TextView)findViewById(R.id.price);
        tv_sales = (TextView)findViewById(R.id.sales);
        tv_zonghe.setOnClickListener(onClickListener);
        tv_price.setOnClickListener(onClickListener);
        tv_sales.setOnClickListener(onClickListener);
        tv_goback = (TextView)findViewById(R.id.goback);
        tv_gosearch= (TextView)findViewById(R.id.gosearch);
        search_et = (EditText)findViewById(R.id.search_et);
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO回车键按下时要执行的操作
                    //Toast.makeText(getActivity(),search_et.getText().toString(),Toast.LENGTH_SHORT).show();
                    util.setSEARCHEDGOODNAME(search_et.getText().toString());
                    SEARCHEDGOODNAME = util.getSEARCHEDGOODNAME();
                    new Thread(initDataRunnable).start();
                    /*
                    finish();
                    Intent intent = new Intent();
                    intent.setClass(SearchGood.this, SearchGood.class);
                    startActivity(intent);
                    */
                }
                return false;
            }
        });
        tv_goback.setOnClickListener(onClickListener);
        tv_gosearch.setOnClickListener(onClickListener);
        new Thread(initDataRunnable).start();
    }

    private Runnable initDataRunnable = new Runnable(){
        @Override
        public void run() {
            String result="";
            FetchData fetchData = new FetchData();
            if(sortFlag.equals("zonghe")){
                result = fetchData.Good_searchGood(SEARCHEDGOODNAME,util.getGLOBALUSERID());
            }
            else if(sortFlag.equals("price")){
                result = fetchData.Good_searchGoodByPrice(SEARCHEDGOODNAME,util.getGLOBALUSERID());
            }
            else{
                result = fetchData.getSpecificCateGoodsBySalesSearch(SEARCHEDGOODNAME,util.getGLOBALUSERID());
            }
            //result = fetchData.Good_searchGood(SEARCHEDGOODNAME,util.getGLOBALUSERID());
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
                    int id;
                    String goodimg;
                    String goodname;
                    String goodintroduction;
                    String goodprice;
                    String goodcommet;
                    String goodordernum;
                    if(!sortFlag.equals("sales")){
                        id = temp.getJSONObject("allGoods").getInt("goodid");
                        goodimg = temp.getJSONObject("allGoods").getString("img");
                        goodname = temp.getJSONObject("allGoods").getString("goodname");
                        goodintroduction = temp.getJSONObject("allGoods").getString("goodintroduction");
                        goodprice = temp.getJSONObject("allGoods").getString("goodprice");
                        goodcommet = temp.getJSONObject("allGoods").getString("averank");
                        goodordernum = temp.getJSONObject("allGoods").getString("orderCount");
                    }
                    else{
                        id = temp.getInt("goodid");
                        goodimg = temp.getString("img");
                        goodname = temp.getString("goodname");
                        goodintroduction = temp.getString("goodintroduction");
                        goodprice = temp.getString("goodprice");
                        goodcommet = temp.getString("averank");
                        goodordernum = temp.getString("orderCount");
                    }

                    allGoodsID[i] = String.valueOf(id);
                    allGoodses[i] = new AllGoods(goodimg,goodname,goodintroduction,goodprice,goodcommet,goodordernum);
                }
                util.setGoodsGroup(allGoodsID);
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

        if(util.getPreActivityFlag() == 0){
            util.setActivityFlag(1);
        }
        else if(util.getPreActivityFlag() == 1){
            util.setActivityFlag(6);
            util.setGoodsGroup(util.getPreGoodsGroup());
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(util.getPreActivityFlag() == 0){
                util.setActivityFlag(1);
            }
            else if(util.getPreActivityFlag() == 1){
                util.setActivityFlag(6);
                util.setGoodsGroup(util.getPreGoodsGroup());
            }
            finish();
        }
        return true;
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
                case R.id.zonghe:
                    setZonghe();
                    break;
                case R.id.price:
                    setPrice();
                    break;
                case R.id.sales:
                    setSales();
                    break;
            }
        }
    };

    private void setZonghe(){
        tv_zonghe.setTextColor(getResources().getColor(R.color.red));
        tv_price.setTextColor(getResources().getColor(R.color.black));
        tv_sales.setTextColor(getResources().getColor(R.color.black));
        sortFlag = "zonghe";
        new Thread(initDataRunnable).start();
    }

    private void setPrice(){
        tv_zonghe.setTextColor(getResources().getColor(R.color.black));
        tv_price.setTextColor(getResources().getColor(R.color.red));
        tv_sales.setTextColor(getResources().getColor(R.color.black));
        sortFlag = "price";
        new Thread(initDataRunnable).start();
    }

    private void setSales(){
        tv_zonghe.setTextColor(getResources().getColor(R.color.black));
        tv_price.setTextColor(getResources().getColor(R.color.black));
        tv_sales.setTextColor(getResources().getColor(R.color.red));
        sortFlag = "sales";
        new Thread(initDataRunnable).start();
    }

    private void goBack(){

            if(util.getPreActivityFlag() == 0){
                util.setActivityFlag(1);
            }
            else if(util.getPreActivityFlag() == 1){
                util.setActivityFlag(6);
                util.setGoodsGroup(util.getPreGoodsGroup());
            }
            finish();
    }

    private void goSearch(){
        //util.setSEARCHEDGOODNAME(search_et.getText().toString());
        /*
        finish();
        Intent intent = new Intent();
        intent.setClass(SearchGood.this, SearchGood.class);
        startActivity(intent);
        */
        //new Thread(initDataRunnable).start();

        util.setSEARCHEDGOODNAME(search_et.getText().toString());
        SEARCHEDGOODNAME = util.getSEARCHEDGOODNAME();
        new Thread(initDataRunnable).start();
    }
}
