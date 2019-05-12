package com.cateringpartner.cyhb.specificCategory;

import android.app.Activity;
import android.content.Intent;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class CategoryDetail extends Activity {

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private String gooditemname;
    public Util util;
    private AllGoods[] allGoodses;
    private String[] allGoodsID;
    private EditText search_et;
    //private Banner banner;
    private List<URL> images;
    private Integer[] promotionID;
    private TextView tv_gosearch;
    private TextView tv_goback;
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
        util.setPreActivityFlag(1);
        util.setPreGoodDetailActivityFlag(6);
        Bundle bundle = this.getIntent().getExtras();
        gooditemname = bundle.getString("gooditemname");

        initData();

    }

    private void initData(){
        tv_goback = (TextView)findViewById(R.id.goback);
        tv_gosearch = (TextView)findViewById(R.id.gosearch);
        tv_zonghe = (TextView)findViewById(R.id.zonghe);
        tv_price = (TextView)findViewById(R.id.price);
        tv_sales = (TextView)findViewById(R.id.sales);
        tv_goback.setOnClickListener(onClickListener);
        tv_gosearch.setOnClickListener(onClickListener);
        tv_zonghe.setOnClickListener(onClickListener);
        tv_price.setOnClickListener(onClickListener);
        tv_sales.setOnClickListener(onClickListener);
        search_et = (EditText)findViewById(R.id.search_et);
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO回车键按下时要执行的操作
                    //Toast.makeText(getActivity(),search_et.getText().toString(),Toast.LENGTH_SHORT).show();
                    util.setSEARCHEDGOODNAME(search_et.getText().toString());
                    Intent intent = new Intent();
                    intent.setClass(CategoryDetail.this, SearchGood.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        new Thread(initDataRunnable).start();
        //banner = (Banner)findViewById(R.id.banner);
        //设置图片加载器
        //banner.setImageLoader(new GlideImageLoader());
        //new Thread(getBannerRunnable).start();
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

    private void goBack(){
        util.setActivityFlag(1);
        finish();
    }

    private void goSearch(){
        util.setPreActivityFlag(1);
        util.setSEARCHEDGOODNAME(search_et.getText().toString());
        Intent intent = new Intent();
        intent.setClass(CategoryDetail.this, SearchGood.class);
        startActivity(intent);
    }

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
                promotionID = new Integer[arr.length()];
                images = new ArrayList<URL>();
                URL url;
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = (JSONObject) arr.get(i);
                    promotionID[i] = jsonObject.getInt("uid");
                    url = new URL("http://47.92.74.241:8080/confimg/promotion/" + promotionID[i] + ".jpg");
                    images.add(url);
                }
                /*
                banner.setImages(images);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        //Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("promotionCompanyId",promotionID[position]+"");
                        intent.putExtras(bundle);
                        intent.setClass(CategoryDetail.this, PromotionDetail.class);
                        startActivity(intent);
                    }
                });
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                */
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    private Runnable initDataRunnable = new Runnable(){
        @Override
        public void run() {
            String result="";
            FetchData fetchData = new FetchData();
            if(sortFlag.equals("zonghe")){
                result = fetchData.getSpecificCateGoods(gooditemname,util.getGLOBALUSERID());
            }
            else if(sortFlag.equals("sales")){
                result = fetchData.getSpecificCateGoodsBySales(gooditemname,util.getGLOBALUSERID());
            }
            else{
                result = fetchData.getSpecificCateGoodsByPrice(gooditemname,util.getGLOBALUSERID());
            }
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
                    int id = temp.getInt("goodid");
                    String goodimg = temp.getString("img");
                    String goodname = temp.getString("goodname");
                    String goodintroduction = temp.getString("goodintroduction");
                    String goodprice = temp.getString("goodprice");
                    String goodcommet = temp.getString("averank");
                    String goodordernum = temp.getString("orderCount");
                    allGoodsID[i] = String.valueOf(id);
                    allGoodses[i] = new AllGoods(goodimg,goodname,goodintroduction,goodprice,goodcommet,goodordernum);
                }
                util.setGoodsGroup(allGoodsID);
                util.setPreGoodsGroup(allGoodsID);
                showData();
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };

    private void showData(){
        /*
        mAdapter = new MultiTypeAdapter(this);
        mRecyclerView.removeAllViews();
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
        */
        mAdapter = new MultiTypeAdapter(this);
        mRecyclerView = (RefreshRecyclerView)findViewById(R.id.recycle_view);
        //mRecyclerView.removeAllViews();
        //mAdapter.notifyDataSetChanged();
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
        util.setActivityFlag(1);
    }
}
