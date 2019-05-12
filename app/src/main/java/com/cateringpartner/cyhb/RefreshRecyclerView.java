package com.cateringpartner.cyhb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.RecyclerAdapter;
import com.cateringpartner.cyhb.application.ApplicationUtil;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.goodDetail.goodDetail;
import com.cateringpartner.cyhb.goodDetail.shopGoodDetail;
import com.cateringpartner.cyhb.home.RecyclerItemOnTouchListener;
import com.cateringpartner.cyhb.myorder.ConfirmAndComment;
import com.cateringpartner.cyhb.newpay.Payment;
import com.cateringpartner.cyhb.promotion.PromotionDetail;
import com.cateringpartner.cyhb.specificCategory.CategoryDetail;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linlongxin on 2016/1/24.
 */
public class RefreshRecyclerView extends FrameLayout {

    private final String TAG = "RefreshRecyclerView";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private boolean loadMoreAble;
    public Util util;
    public ApplicationUtil applicationUtil;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        util = new Util();
        applicationUtil = new ApplicationUtil();
        View view = inflate(context, R.layout.view_refresh_recycler, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.$_recycler_view);
        try{
            mRecyclerView.addOnItemTouchListener(new RecyclerItemOnTouchListener(mRecyclerView) {
                @Override
                public void onItemClick(RecyclerView.ViewHolder vh) {
                    //Toast.makeText(getContext(), "方法三：单击" + vh.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    /*if(applicationUtil.getActivityFlag().equals(context.getString(R.string.user_mainactivity_home))){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("goodtype",vh.getAdapterPosition()+"");
                        intent.putExtras(bundle);
                        intent.setClass(getContext(), CategoryDetail.class);
                        context.startActivity(intent);
                    }
                    */
                    if(util.getActivityFlag() == 1){
                        //home fragment
                        //if(vh.getAdapterPosition() == 1 || vh.getAdapterPosition() == 0){
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("gooditemname",getItemnameById(vh.getAdapterPosition()));
                            intent.putExtras(bundle);
                            intent.setClass(getContext(), CategoryDetail.class);
                            context.startActivity(intent);
                        //}
                    }
                    else if(util.getActivityFlag() == 2){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("basketorderid",util.getBasketOrderGroupByIndex(vh.getAdapterPosition())+"");
                        intent.putExtras(bundle);
                        intent.setClass(getContext(), Payment.class);
                        context.startActivity(intent);
                    }
                    else if(util.getActivityFlag() == 3){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("promotionCompanyId",util.getGlobalPromotionId()[vh.getAdapterPosition()]+"");
                        intent.putExtras(bundle);
                        intent.setClass(getContext(), PromotionDetail.class);
                        context.startActivity(intent);
                    }
                    else if(util.getActivityFlag() == 4){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("basketorderid",util.getBasketOrderGroupByIndex(vh.getAdapterPosition())+"");
                        intent.putExtras(bundle);
                        intent.setClass(getContext(), ConfirmAndComment.class);
                        context.startActivity(intent);
                    }
                    else if(util.getActivityFlag() == 6){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("goodid",util.getGoodsID(vh.getAdapterPosition()));
                        intent.putExtras(bundle);
                        //util.setPreGoodDetailActivityFlag(6);
                        intent.setClass(getContext(), goodDetail.class);
                        context.startActivity(intent);
                    }
                    else if(util.getActivityFlag() == 555){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("goodid",util.getGoodsIDShop(vh.getAdapterPosition()));
                        //bundle.putString("goodid",util.getGoodsGroupShop()[vh.getAdapterPosition()]);
                        intent.putExtras(bundle);
                        intent.setClass(getContext(), shopGoodDetail.class);
                        //GoodByCompany.test_a.finish();

                        context.startActivity(intent);
                    }
                }

                @Override
                public void onLongItemClick(RecyclerView.ViewHolder vh) {
                    if(util.getActivityFlag() == 5){
                        util.setDelFlag(util.getBasketOrderGroup()[vh.getAdapterPosition()]+"");
                        //Toast.makeText(getContext(), "订单id" + util.getBasketOrderGroup()[vh.getAdapterPosition()], Toast.LENGTH_SHORT).show();
                        showNormalDialog();
                    }
                }
            });
        }
        catch (Exception ex){
            Log.e("root",ex.toString());
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.$_refresh_layout);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshRecyclerView);
        boolean refreshAble = typedArray.getBoolean(R.styleable.RefreshRecyclerView_refresh_able, true);
        loadMoreAble = typedArray.getBoolean(R.styleable.RefreshRecyclerView_load_more_able, true);
        if (!refreshAble) {
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("是否删除这个订单？");
        normalDialog.setMessage("");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        new Thread(delRunnable).start();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    private Runnable delRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.Order_DelOrder(util.getDelFlag());
            Log.e("login result", result);
            Message msg = new Message();
            msg.obj = result;
            delHandler.sendMessage(msg);
        }
    };

    //
    Handler delHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String isDel = jsonObject.getString("status");
                if(isDel.equals("1")){
                    Toast.makeText(getContext(),"删除订单成功，请刷新",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"删除订单失败",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };

    public void setAdapter(RecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mAdapter = adapter;
        mAdapter.loadMoreAble = loadMoreAble;
    }

    public void setLayoutManager(final RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = mAdapter.getItemViewType(position);
                    if (type == RecyclerAdapter.HEADER_TYPE
                            || type == RecyclerAdapter.FOOTER_TYPE
                            || type == RecyclerAdapter.STATUS_TYPE) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    public void setRefreshAction(final Action action) {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                action.onAction();
            }
        });
    }

    public void setLoadMoreAction(final Action action) {
        Log.d(TAG, "setLoadMoreAction");
        if (mAdapter.isShowNoMore || !loadMoreAble) {
            return;
        }
        mAdapter.loadMoreAble = true;
        mAdapter.setLoadMoreAction(action);
    }

    public void showNoMore() {
        mAdapter.showNoMore();
    }

    public void setItemSpace(int left, int top, int right, int bottom) {
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(left, top, right, bottom));
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public TextView getNoMoreView() {
        return mAdapter.mNoMoreView;
    }

    public void setSwipeRefreshColorsFromRes(@ColorRes int... colors) {
        mSwipeRefreshLayout.setColorSchemeResources(colors);
    }

    /**
     * 8位16进制数 ARGB
     */
    public void setSwipeRefreshColors(@ColorInt int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    public void showSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void dismissSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public String getItemnameById(int positionID){
        Map<String, String> map = new HashMap<>();
        map.put("0","蔬菜");
        map.put("1","水果干果");
        map.put("2","鲜猪肉");
        map.put("3","牛羊肉");
        map.put("4","冷冻品");
        map.put("5","海鲜水产");
        map.put("6","调味品");
        map.put("7","干货");
        map.put("8","禽蛋类");
        map.put("9","粮油");
        map.put("10","熟食拌菜");
        map.put("11","酒水茶叶");
        map.put("12","厨房燃料");
        map.put("13","餐厨灶具");
        map.put("14","厨房设备");
        map.put("15","有机专区");
        map.put("16","土特产");
        map.put("17","豆制品");
        map.put("18","农户专区");
        map.put("19","预包装食品");
        return map.get(positionID+"");
    }
}
