package com.cateringpartner.cyhb.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.application.ApplicationUtil;
import com.cateringpartner.cyhb.bean.Category;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.goodDetail.goodDetail;
import com.cateringpartner.cyhb.promotion.PromotionDetail;
import com.cateringpartner.cyhb.specificCategory.SearchGood;
import com.cateringpartner.cyhb.util.GlideImageLoader;
import com.cateringpartner.cyhb.util.Util;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AniChikage on 2017/6/2.
 */

public class HomeFragment extends Fragment {

    private View view;
    private EditText search_et;
    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private Banner banner;
    private List<URL> images;
    private int mPage = 0;
    private Util util;
    private ApplicationUtil applicationUtil;
    private Integer[] promotionID;
    private TextView tv_gosearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        initIDandView();
        util = new Util();
        util.setPreActivityFlag(0);
        applicationUtil = new ApplicationUtil();
        return view;
    }

    private void initIDandView(){
        search_et = (EditText)view.findViewById(R.id.search_et);
        tv_gosearch = (TextView)view.findViewById(R.id.gosearch);
        tv_gosearch.setOnClickListener(onClickListener);
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO回车键按下时要执行的操作
                    //Toast.makeText(getActivity(),search_et.getText().toString(),Toast.LENGTH_SHORT).show();
                    util.setSEARCHEDGOODNAME(search_et.getText().toString());
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SearchGood.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        mAdapter = new MultiTypeAdapter(getActivity());
        mRecyclerView = (RefreshRecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845,0xFFE44F98,0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));

        banner = (Banner)view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //mAdapter.setHeader(banner);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        new Thread(getBannerRunnable).start();
        //设置图片集合
        try{
            images = new ArrayList<URL>();
            URL url = new URL("http://47.92.74.241:8070/allgoods/1.jpg");
            images.add(url);
            url = new URL("http://47.92.74.241:8070/allgoods/2.jpg");
            images.add(url);
            url = new URL("http://47.92.74.241:8070/allgoods/3.jpg");
            images.add(url);
            url = new URL("http://47.92.74.241:8070/allgoods/4.jpg");
            images.add(url);
            url = new URL("http://47.92.74.241:8070/allgoods/5.jpg");
            images.add(url);
            url = new URL("http://47.92.74.241:8070/allgoods/6.jpg");
            images.add(url);
            banner.setImages(images);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    //Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("goodid",String.valueOf(position+1));
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(), goodDetail.class);
                    startActivity(intent);
                }
            });
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
        catch (Exception ex){
        }
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.gosearch:
                    goSearch();
                    break;
            }
        }
    };

    private void goSearch(){
        util.setSEARCHEDGOODNAME(search_et.getText().toString());
        util.setPreActivityFlag(0);
        Intent intent = new Intent();
        intent.setClass(getActivity(), SearchGood.class);
        startActivity(intent);
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
                mAdapter.addAll(CategoryHolder.class, getTextImageVirualData());
                if (mPage >= 0) {
                    //mAdapter.showNoMore();
                }
                if(isRefresh){
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1000);
    }

    //
    public Category[] getTextImageVirualData() {
        return new Category[]{
                new Category("http://47.92.74.241:8070/goodsCategory/shucai.png", "蔬菜"),
                new Category("http://47.92.74.241:8070/goodsCategory/shuiguo.png", "水果干果"),
                new Category("http://47.92.74.241:8070/goodsCategory/lengxianrou.png", "鲜猪肉"),
                new Category("http://47.92.74.241:8070/goodsCategory/xianrou.png", "牛羊肉"),
                new Category("http://47.92.74.241:8070/goodsCategory/lengdongpin.png", "冷冻品"),
                new Category("http://47.92.74.241:8070/goodsCategory/shuichanpin.png", "海鲜水产"),
                new Category("http://47.92.74.241:8070/goodsCategory/tiaoweipin.png", "调味品"),
                new Category("http://47.92.74.241:8070/goodsCategory/sanzhuangshipin.png", "干货"),
                new Category("http://47.92.74.241:8070/goodsCategory/temp.png", "禽蛋类"),
                new Category("http://47.92.74.241:8070/goodsCategory/liangyou.png", "粮油"),
                new Category("http://47.92.74.241:8070/goodsCategory/yubaozhuangshipin.png", "熟食拌菜"),
                new Category("http://47.92.74.241:8070/goodsCategory/jiushui.png", "酒水茶叶"),
                new Category("http://47.92.74.241:8070/goodsCategory/chufangranliao.png", "厨房燃料"),
                new Category("http://47.92.74.241:8070/goodsCategory/canju.png", "餐厨灶具"),
                new Category("http://47.92.74.241:8070/goodsCategory/riyongpin.png", "厨房设备"),
                new Category("http://47.92.74.241:8070/goodsCategory/temp.png", "有机专区"),
                new Category("http://47.92.74.241:8070/goodsCategory/tutechan.png", "土特产"),
                new Category("http://47.92.74.241:8070/goodsCategory/douzhipin.png", "豆制品"),
                new Category("http://47.92.74.241:8070/goodsCategory/nonghuzhuanqu.png", "农户专区"),
                new Category("http://47.92.74.241:8070/goodsCategory/yubaozhuangshipin.png", "预包装食品"),
        };
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
                    String img = jsonObject.getString("companylogoimg");
                    url = new URL(img);
                    images.add(url);
                }
                banner.setImages(images);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        //Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("promotionCompanyId",promotionID[position]+"");
                        intent.putExtras(bundle);
                        intent.setClass(getActivity(), PromotionDetail.class);
                        startActivity(intent);
                    }
                });
                //banner设置方法全部调用完毕时最后调用
                banner.start();

            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
}
