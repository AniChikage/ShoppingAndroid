package com.cateringpartner.cyhb.basket;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.materialcab.MaterialCab;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.newpay.Payment;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by AniChikage on 2017/6/2.
 */

// activity click id:7e
public class BasketFragment extends Fragment implements BasketAdapter.Listener, MaterialCab.Callback {

    private View view;

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;
    private Util util;
    private Baskets[] allBaskets;
    private Integer[] basketOrderGroup;
    private DragSelectRecyclerView listView;
    private BasketAdapter adapter;
    private TextView tv_gopay;
    private TextView tv_select_all;
    private TextView tv_delete;
    private Context context;
    private String[] goodnames;
    private String[] goodimgs;
    private String[] goodsingleprices;
    private String[] goodgoodnums;
    private String[] goodtotalprices;
    private String[] goodbasketgoodid;
    private String[] delOrderId;
    private String[] payOrderId;
    private int basketOidNum=0;
    private int curNum=0;
    private boolean isSelected=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_myorder, container, false);
        initIDandView();
        //adapter = new BasketAdapter(BasketFragment.this);
        context = getActivity();
        //初始化ID
        tv_gopay = (TextView)view.findViewById(R.id.tv_gopay);
        tv_select_all = (TextView)view.findViewById(R.id.tv_select_all);
        tv_delete = (TextView)view.findViewById(R.id.tv_delete);
        tv_gopay.setOnClickListener(onClickListener);
        tv_select_all.setOnClickListener(onClickListener);
        tv_delete.setOnClickListener(onClickListener);

        // Setup the RecyclerView
        listView = (DragSelectRecyclerView) view.findViewById(R.id.list);
        listView.setLayoutManager(
                new GridLayoutManager(context, getResources().getInteger(R.integer.grid_width)));
        //listView.setAdapter(adapter);
        return view;
    }

    private void initIDandView() {
        util = new Util();
        showData();
        showSelect();
    }

    private void showSelect(){

    }

    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.BasketGetBasketByUid(util.getGLOBALUSERID());
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
                allBaskets = new Baskets[arr.length()];
                basketOrderGroup = new Integer[arr.length()];

                goodnames = new String[arr.length()];
                goodimgs = new String[arr.length()];
                goodsingleprices = new String[arr.length()];
                goodgoodnums = new String[arr.length()];
                goodtotalprices = new String[arr.length()];
                goodbasketgoodid = new String[arr.length()];
                for(int i=0;i<arr.length();++i){
                    JSONObject jsonObject = (JSONObject)arr.get(i);
                    goodnames[i] = jsonObject.getJSONObject("allGoods").getString("goodname");
                    goodimgs[i] = jsonObject.getJSONObject("allGoods").getString("img");
                    goodsingleprices[i] = jsonObject.getJSONObject("allGoods").getString("goodprice");
                    goodtotalprices[i] = jsonObject.getJSONObject("orders").getString("totalprice");
                    goodgoodnums[i] = jsonObject.getJSONObject("orders").getString("goodnum");
                    goodbasketgoodid[i] = jsonObject.getJSONObject("orders").getString("oid");
                }
                basketOidNum = arr.length();
                util.setGoodnames(goodnames);
                util.setGoodimgs(goodimgs);
                util.setGoodsingleprices(goodsingleprices);
                util.setGoodgoodnums(goodgoodnums);
                util.setGoodtotalprices(goodtotalprices);
                util.setGoodbasketorderid(goodbasketgoodid);
                Log.e("555555555555555555555",util.getGoodnames()[0]);
                adapter = new BasketAdapter(BasketFragment.this,getActivity());
                listView.removeAllViews();
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                //getData(true);
                //util.setBasketOrderGroup(basketOrderGroup);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
                adapter = new BasketAdapter(BasketFragment.this,getActivity());
                listView.removeAllViews();
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        }
    };

    public void callback(){

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


    ////////////////////////////
    // Material CAB Callbacks
    @Override
    public void onClick(int index) {
        adapter.toggleSelected(index);
    }

    @Override
    public void onLongClick(int index) {
        listView.setDragSelectActive(true, index);
    }

    @Override
    public void onSelectionChanged(int count) {
        if (count > 0) {

        }
    }

    // Material CAB Callbacks

    @Override
    public boolean onCabCreated(MaterialCab cab, Menu menu) {
        return true;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onCabItemClicked(MenuItem item) {

        return true;
    }

    @Override
    public boolean onCabFinished(MaterialCab cab) {
        adapter.clearSelected();
        return true;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_gopay:
                    goPay();
                    break;
                case R.id.tv_select_all:
                    doSelectAll();
                    break;
                case R.id.tv_delete:
                    doDelete();
                    break;
            }
        }
    };

    public void doDelete() {
        /*
        StringBuilder sb = new StringBuilder();
        int traverse = 0;
        for (Integer index : adapter.getSelectedIndices()) {
            if (traverse > 0) sb.append(", ");
            sb.append(adapter.getOid(index));
            traverse++;
        }
*/
        curNum = 0;
        int temp=0;
        delOrderId = new String[adapter.getSelectedIndices().size()];
        for (Integer index : adapter.getSelectedIndices()) {
            delOrderId[temp] = adapter.getOid(index);
            temp +=1;
        }
        /*
        for (Integer index : adapter.getSelectedIndices()) {
            if (traverse > 0) sb.append(", ");
            sb.append(adapter.getOid(index));
            traverse++;
        }
        Log.e("getSelectedIndices",adapter.getSelectedIndices().toString());
        //Log.e("",);
*/
        if(adapter.getSelectedIndices().size() != 0){
            new Thread(delOrderRunnable).start();
        }

        /*Toast.makeText(context,
                String.format(
                        "Selected letters (%d): %s", adapter.getSelectedIndices().size(), sb.toString()),
                Toast.LENGTH_LONG)
                .show();
                */
        adapter.clearSelected();
    }

    public void doSelectAll(){
        if(!isSelected){
            for(int i=0;i<basketOidNum;++i){
                adapter.setSelected(i,true);
            }
            isSelected = !isSelected;
        }
        else{
            for(int i=0;i<basketOidNum;++i) {
                adapter.setSelected(i, false);
            }
            isSelected = !isSelected;
        }
    }

    public void goPay(){
        int temp=0;
        Double totalprice=0.0;
        payOrderId = new String[adapter.getSelectedIndices().size()];
        for (Integer index : adapter.getSelectedIndices()) {
            payOrderId[temp] = adapter.getOid(index);
            totalprice+=Double.valueOf(adapter.getPrice(index).replace("元",""));
            temp +=1;
        }
        if(adapter.getSelectedIndices().size() != 0){
            util.setTopayorderids(payOrderId);
            util.setTotalprice(totalprice+"");
            Intent intent = new Intent(getActivity(), Payment.class);
            startActivity(intent);
        }
    }

    private Runnable delOrderRunnable = new Runnable() {
        @Override
        public void run() {
            String result="";
            FetchData fetchData = new FetchData();
            //for(int i=0;i<delOrderId.length;i++){
            result = fetchData.delBasketOrder(delOrderId[curNum]);
            //}
            Log.e("delorder", result);
            Message msg = new Message();
            msg.obj = result;
            delOrderHandler.sendMessage(msg);
        }
    };

    //
    Handler delOrderHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if(curNum<delOrderId.length-1){
                    curNum+=1;
                    new Thread(delOrderRunnable).start();
                }
                else{
                    //listView.setAdapter(null);
                    //BasketAdapter basketAdapter = new BasketAdapter(BasketFragment.this,getActivity());
                    new Thread(initDataRunnable).start();
                    //listView.setAdapter(basketAdapter);
                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT)
                            .show();
                }

                //util.setBasketOrderGroup(basketOrderGroup);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
}
