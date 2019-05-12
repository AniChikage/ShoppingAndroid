package com.cateringpartner.cyhb.goodDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.RefreshRecyclerView;
import com.cateringpartner.cyhb.adapter.Action;
import com.cateringpartner.cyhb.adapter.MultiTypeAdapter;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.specificCategory.GoodByCompany;
import com.cateringpartner.cyhb.util.Util;
import com.cateringpartner.cyhb.widget.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AniChikage on 2017/6/6.
 */

public class goodDetail extends Activity{

    private Util util;
    private Status status;
    private String goodid;
    private String global_goodname;
    private String global_company;
    private String global_companyid;
    private TextView tv_price;
    private TextView tv_goodname;
    private TextView tv_company;
    private TextView tv_deliveryprice;
    private TextView tv_totalprice;
    private TextView tv_addtobasket;
    private TextView tv_address;
    private TextView tv_totaldeliveryprice;
    private TextView tv_good;
    private TextView tv_comment;
    private TextView tv_detail;
    private TextView tv_introduction;
    private TextView tv_goodintroduction;
    private TextView tv_introductiondetail;
    private TextView tv_params;
    private TextView tv_shop;
    private TextView tv_shopmaster;
    private TextView tv_shopphone;
    private TextView tv_commentfirstuser;
    private TextView tv_commnetfirst;
    private TextView tv_showallcomment;
    private TextView tv_commenttitle;
    private TextView tv_after;
    private TextView tv_mainparams;
    private ImageView iv_image;
    private ImageView iv_minus;
    private ImageView iv_plus;
    private ImageView iv_back;
    private ImageView iv_introimg;
    private EditText et_goodnum;
    private Comments[] comments;
    private LinearLayout ll_goodinfo;
    private LinearLayout ll_goodcomment;
    private LinearLayout ll_gooddetail;
    private LinearLayout ll_introduction;
    private LinearLayout ll_params;
    private LinearLayout ll_after;
    private LinearLayout ll_introductionimg;
    private LinearLayout[] linearLayouts;
    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.good_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        util = new Util();
        status = new Status();
        //util.setActivityFlag(7);
        Bundle bundle = this.getIntent().getExtras();
        goodid = bundle.getString("goodid");
        Log.e("goodid",goodid);
        initData();
    }

    //region 初始化数据
    private void initData() {
        tv_price = (TextView) findViewById(R.id.price);
        tv_company = (TextView) findViewById(R.id.company);
        tv_deliveryprice = (TextView) findViewById(R.id.deliveryprice);
        tv_goodname = (TextView) findViewById(R.id.goodname);
        tv_totalprice = (TextView) findViewById(R.id.totalprice);
        tv_addtobasket = (TextView) findViewById(R.id.addtobasket);
        tv_totaldeliveryprice = (TextView) findViewById(R.id.totaldeliveryprice);
        tv_good = (TextView) findViewById(R.id.good);
        tv_comment = (TextView) findViewById(R.id.comment);
        tv_detail = (TextView) findViewById(R.id.detail);
        tv_introduction = (TextView) findViewById(R.id.introduction);
        tv_params = (TextView) findViewById(R.id.params);
        tv_address = (TextView) findViewById(R.id.address);
        tv_shop = (TextView) findViewById(R.id.shop);
        tv_shopmaster = (TextView) findViewById(R.id.shopmaster);
        tv_shopphone = (TextView) findViewById(R.id.shopphone);
        tv_introductiondetail = (TextView) findViewById(R.id.introductiondetail);
        tv_goodintroduction = (TextView) findViewById(R.id.goodintroduction);
        tv_commentfirstuser = (TextView) findViewById(R.id.commentfirstuser);
        tv_commnetfirst = (TextView) findViewById(R.id.commentfirst);
        tv_showallcomment = (TextView) findViewById(R.id.showallcommet);
        tv_commenttitle = (TextView) findViewById(R.id.commenttitle);
        tv_after = (TextView) findViewById(R.id.after);
        tv_mainparams = (TextView) findViewById(R.id.mainparams);
        iv_image = (ImageView) findViewById(R.id.image);
        iv_minus = (ImageView) findViewById(R.id.iv_minus);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
        iv_back = (ImageView) findViewById(R.id.back);
        iv_introimg = (ImageView) findViewById(R.id.introimg);
        et_goodnum = (EditText) findViewById(R.id.goodnum);
        ll_goodinfo = (LinearLayout) findViewById(R.id.goodinfo);
        ll_goodcomment = (LinearLayout) findViewById(R.id.goodcomment);
        ll_gooddetail = (LinearLayout) findViewById(R.id.gooddetail);
        ll_introduction = (LinearLayout) findViewById(R.id.llintroduction);
        ll_introductionimg = (LinearLayout) findViewById(R.id.llintroductionimg);
        ll_params = (LinearLayout) findViewById(R.id.llparams);
        ll_after = (LinearLayout) findViewById(R.id.llafter);
        linearLayouts = new LinearLayout[5];
        linearLayouts[0] = ll_goodinfo;
        linearLayouts[1] = ll_goodcomment;
        linearLayouts[2] = ll_gooddetail;
        linearLayouts[3] = ll_introduction;
        linearLayouts[4] = ll_params;
        tv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_good.setTextColor(getResources().getColor(R.color.red));
                tv_comment.setTextColor(getResources().getColor(R.color.black));
                tv_detail.setTextColor(getResources().getColor(R.color.black));
                ll_goodinfo.setVisibility(View.VISIBLE);
                ll_goodcomment.setVisibility(View.INVISIBLE);
                ll_gooddetail.setVisibility(View.INVISIBLE);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_good.setTextColor(getResources().getColor(R.color.black));
                tv_comment.setTextColor(getResources().getColor(R.color.red));
                tv_detail.setTextColor(getResources().getColor(R.color.black));
                ll_goodinfo.setVisibility(View.INVISIBLE);
                ll_goodcomment.setVisibility(View.VISIBLE);
                ll_gooddetail.setVisibility(View.INVISIBLE);
                util.setActivityFlag(113);
                //goShowComment();
            }
        });

        tv_showallcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_good.setTextColor(getResources().getColor(R.color.black));
                tv_comment.setTextColor(getResources().getColor(R.color.red));
                tv_detail.setTextColor(getResources().getColor(R.color.black));
                ll_goodinfo.setVisibility(View.INVISIBLE);
                ll_goodcomment.setVisibility(View.VISIBLE);
                ll_gooddetail.setVisibility(View.INVISIBLE);
                ll_after.setVisibility(View.INVISIBLE);
                util.setActivityFlag(113);
                //goShowComment();
            }
        });

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_good.setTextColor(getResources().getColor(R.color.black));
                tv_comment.setTextColor(getResources().getColor(R.color.black));
                tv_detail.setTextColor(getResources().getColor(R.color.red));
                ll_goodinfo.setVisibility(View.INVISIBLE);
                ll_goodcomment.setVisibility(View.INVISIBLE);
                ll_gooddetail.setVisibility(View.VISIBLE);
                ll_introduction.setVisibility(View.VISIBLE);
                ll_params.setVisibility(View.INVISIBLE);
                ll_after.setVisibility(View.INVISIBLE);
                tv_introduction.setTextColor(getResources().getColor(R.color.red));
                tv_params.setTextColor(getResources().getColor(R.color.black));
                tv_after.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tv_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_introduction.setVisibility(View.VISIBLE);
                ll_params.setVisibility(View.INVISIBLE);
                ll_after.setVisibility(View.INVISIBLE);
                tv_introduction.setTextColor(getResources().getColor(R.color.red));
                tv_params.setTextColor(getResources().getColor(R.color.black));
                tv_after.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tv_params.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_introduction.setVisibility(View.INVISIBLE);
                ll_params.setVisibility(View.VISIBLE);
                ll_after.setVisibility(View.INVISIBLE);
                tv_introduction.setTextColor(getResources().getColor(R.color.black));
                tv_params.setTextColor(getResources().getColor(R.color.red));
                tv_after.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tv_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_introduction.setVisibility(View.INVISIBLE);
                ll_params.setVisibility(View.INVISIBLE);
                ll_after.setVisibility(View.VISIBLE);
                tv_introduction.setTextColor(getResources().getColor(R.color.black));
                tv_params.setTextColor(getResources().getColor(R.color.black));
                tv_after.setTextColor(getResources().getColor(R.color.red));
            }
        });

        /*
        tv_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.setGLOBALCOMPANY(tv_company.getText().toString().trim());
                Intent intent = new Intent();
                intent.setClass(goodDetail.this, GoodByCompany.class);
                startActivity(intent);
            }
        });
        */

        tv_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.setGLOBALCOMPANY(tv_company.getText().toString().trim());
                util.setGLOBALCOMPANYID(global_companyid);
                Intent intent = new Intent();
                intent.setClass(goodDetail.this, GoodByCompany.class);
                startActivity(intent);
                //finish();
            }
        });

        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(et_goodnum.getText().toString())>1){
                    try{
                    et_goodnum.setText(Integer.valueOf(et_goodnum.getText().toString())-1+"");
                    }
                    catch (Exception ex){

                    }
                }
            }
        });
        try{
            iv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        et_goodnum.setText(Integer.valueOf(et_goodnum.getText().toString())+1+"");
                    }
                    catch (Exception ex){

                    }
                }
            });
        }
        catch (Exception ex){
            Log.e("erre",ex.toString());
        }

        et_goodnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count != 0){

                    Log.e("ohno","ohno");
                    String delivery = tv_deliveryprice.getText().toString().trim().replace("免邮","").replace("元/斤","");
                    double totalprice;
                    /*if(delivery != ""){
                        if(Double.valueOf(et_goodnum.getText().toString()) <= 75){
                            totalprice = (Double.valueOf(delivery) + Double.valueOf(tv_price.getText().toString().trim().replace("元/斤","")))*Double.valueOf(et_goodnum.getText().toString());
                            tv_totaldeli veryprice.setText("运费:¥"+String.format("%.2f",Double.valueOf(et_goodnum.getText().toString())*Double.valueOf(delivery)));
                        }
                        else{
                            totalprice = (Double.valueOf(tv_price.getText().toString().trim().replace("元/斤","")))*Double.valueOf(et_goodnum.getText().toString())+15;
                            tv_totaldeliveryprice.setText("运费:¥15.00");
                        }
                    }
                    else{
                        totalprice = (Double.valueOf(tv_price.getText().toString().trim().replace("元/斤","")))*Double.valueOf(et_goodnum.getText().toString());
                    }
                    */
                    String singlepricestr = tv_price.getText().toString().trim();
                    String singleprice = "";
                    String regex = "\\d+(.\\d+)?";
                    Pattern p = Pattern.compile(regex);

                    Matcher m = p.matcher(singlepricestr);
                    while (m.find()) {
                        if (!"".equals(m.group())){
                            System.out.println("come here:" + m.group());
                            singleprice = m.group();
                        }
                        break;
                    }
                    totalprice = Double.valueOf(singleprice)*Double.valueOf(et_goodnum.getText().toString());
                    tv_totalprice.setText("¥"+String.format("%.2f",totalprice));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*
                */

            }
        });
        tv_addtobasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"sdfs",Toast.LENGTH_SHORT).show();
                addOrder();
            }
        });
        new Thread(initDataRunnable).start();
        goShowComment();
    }

    private Runnable initDataRunnable = new Runnable(){
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getGoodDetail(goodid);
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
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    int id = temp.getJSONObject("allGoods").getInt("goodid");
                    String goodname = temp.getJSONObject("allGoods").getString("goodname");
                    global_goodname = goodname;
                    try{
                        String imgpath = "http://47.92.74.241:8070/images/goodimages/";
                        for(int ii=0;ii<Integer.valueOf(temp.getJSONObject("allGoods").getString("goodintroimgnum"));ii++){
                            ImageView imageView = new ImageView(goodDetail.this);
                            Glide.with(goodDetail.this)
                                    .load(imgpath+id+"_"+ii+".jpg")
                                    .into(imageView);
                            ll_introductionimg.addView(imageView);
                        }
                    }catch (Exception e){

                    }
                    String goodprice = temp.getJSONObject("allGoods").getString("goodprice");
                    String goodcompany = temp.getJSONObject("user").getString("company");
                    global_company = goodcompany;
                    String gooddelivery;
                    try{
                        gooddelivery = temp.getJSONObject("allGoods").getString("gooddelivery");
                    }
                    catch (Exception ex){
                        gooddelivery = "0";
                    }
                    String introductiondetail = temp.getJSONObject("allGoods").getString("goodintroduction");
                    global_companyid = temp.getJSONObject("user").getString("uid");
                    String shopmaster = temp.getJSONObject("user").getString("realname");
                    String shopphone = "";
                    try{
                        shopphone = temp.getJSONObject("user").getString("phone");
                    }
                    catch (Exception e){

                    }
                    util.setGLOBALCOMPANYID(global_companyid);
                    String address = temp.getJSONObject("user").getString("usercity")+temp.getJSONObject("user").getString("district")+temp.getJSONObject("user").getString("address");
                    tv_price.setText(goodprice);
                    tv_goodname.setText(goodname);
                    tv_company.setText(goodcompany);
                    tv_deliveryprice.setText(gooddelivery);
                    tv_address.setText(address);
                    tv_shopmaster.setText(shopmaster);
                    tv_shopphone.setText(shopphone);
                    tv_introductiondetail.setText(introductiondetail);
                    tv_goodintroduction.setText(introductiondetail);
                    String params = temp.getJSONObject("allGoods").getString("goodparam");
                    params = params.replace("{","").replace("\"","").replace("}","").replace(",","\n").replace("，","\n");
                    /*
                    Log.e("params",params);
                    TextView textView = new TextView(getApplicationContext());
                    textView.setPadding(10, 10, 10, 10);
                    RelativeLayout.LayoutParams tvLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(tvLayoutParams);
                    textView.setText(params);
                    ll_params.addView(textView);
                    */
                    tv_mainparams.setText(params);
                    Glide.with(getApplicationContext())
                            .load(temp.getJSONObject("allGoods").getString("img"))
                            .into(iv_image);
                    Glide.with(getApplicationContext())
                            .load(temp.getJSONObject("allGoods").getString("img"))
                            .into(iv_introimg);

                    String singlepricestr = tv_price.getText().toString().trim();
                    String singleprice = "";
                    String regex = "\\d+(.\\d+)?";
                    Pattern p = Pattern.compile(regex);

                    Matcher m = p.matcher(singlepricestr);
                    while (m.find()) {
                        if (!"".equals(m.group())){
                            System.out.println("come here:" + m.group());
                            singleprice = m.group();
                        }
                        break;
                    }
                    Double totalprice = Double.valueOf(singleprice)*Double.valueOf(et_goodnum.getText().toString());
                    tv_totalprice.setText("¥"+String.format("%.2f",totalprice));
                }
                //util.setGoodsGroup(allGoodsID);
                //showData();
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };
    //endregion

    private void addOrder(){
        new Thread(addOrderRunnable).start();
    }

    //region 添加订单
    private Runnable addOrderRunnable = new Runnable(){
        @Override
        public void run() {
            Date dt= new Date();
            Long time= dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
            System.out.println(System.currentTimeMillis());//与上面的相同
            String result;
            FetchData fetchData = new FetchData();
            //double sellprice = Double.valueOf(tv_totalprice.getText().toString().trim().replace("¥","").replace("运费:",""))-Double.valueOf(tv_totaldeliveryprice.getText().toString().trim().replace("¥","").replace("运费:",""));
            result = fetchData.addOrder(goodid, global_goodname,util.getGLOBALUSERID(), util.getUserphone(), tv_totalprice.getText().toString().trim().replace("¥",""),"","","0","0","0",time.toString(),global_companyid,global_company,"0","0",et_goodnum.getText().toString().trim());
            Log.e("login result",result);
            Message msg = new Message();
            msg.obj = result;
            addOrderHandler.sendMessage(msg);
        }
    };

    Handler addOrderHandler = new Handler() {
        public void handleMessage(Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String)msg.obj);
                String addResult = jsonObject.getString("status");
                Log.e("addResult",addResult);
                if(addResult.equals("1")){
                    Toast.makeText(getApplicationContext(), R.string.addordersuccess, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.addorderfailed, Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex){
                Log.e("roor",ex.toString());
            }
        }
    };
    //endregion

    //region 显示该商品的评价
    private void goShowComment(){
        mAdapter = new MultiTypeAdapter(this);
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                //getData(true);
                new Thread(getCommentRunnable).start();
            }
        });
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                new Thread(getCommentRunnable).start();
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
    //endregion

    //region 获取商品评价线程
    private Runnable getCommentRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getCommetByGoodid(goodid);
            Log.e("getCommet result", result);
            Message msg = new Message();
            msg.obj = result;
            getCommentHandler.sendMessage(msg);
        }
    };

    Handler getCommentHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                JSONArray arr = new JSONArray((String) msg.obj);
                comments = new Comments[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    String phone = temp.getJSONObject("user").getString("phone");
                    String rank = temp.getJSONObject("comments").getString("rank");
                    String comment = temp.getJSONObject("comments").getString("comment");
                    comments[i] = new Comments(phone, rank,comment);
                    tv_commentfirstuser.setText(phone);
                    tv_commnetfirst.setText(comment);
                }
                tv_commenttitle.setText("评价（"+arr.length()+"）");
                if(arr.length() == 0){
                    tv_commnetfirst.setText("暂无评价");
                }
                getData(true);
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion

    //region getdata
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
                mAdapter.addAll(CommentsHolder.class, comments);
                if (mPage >= 0) {
                    //mAdapter.showNoMore();
                }
                if (isRefresh) {
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1000);
    }
    //endregion

    //region
    private void setVisible(LinearLayout linearLayout){
        for(int i = 0; i < 5; ++i){
            if(linearLayouts[i] == linearLayout){
                linearLayouts[i].setVisibility(View.VISIBLE);
            }
            else{
                linearLayouts[i].setVisibility(View.INVISIBLE);
            }
        }
    }
    //endregion

    //region 销毁回调函数
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            //if(util.getPreGoodDetailActivityFlag() == 555){
            //    util.setActivityFlag(555);
            //}
            //else{
                util.setActivityFlag(6);
            //}
        }
        catch (Exception e){
            util.setActivityFlag(6);
        }
    }
    //endregion

}
