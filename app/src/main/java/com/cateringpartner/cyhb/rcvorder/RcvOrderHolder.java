package com.cateringpartner.cyhb.rcvorder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.adapter.BaseViewHolder;

/**
 * Created by linlongxin on 2016/8/23.
 */

public class RcvOrderHolder extends BaseViewHolder<RcvOrder> implements View.OnClickListener,View.OnLongClickListener {

    private TextView tv_touser;
    private TextView tv_touserphone;
    private TextView tv_touseraddress;
    private TextView tv_totalprice;
    private TextView tv_rcvtime;
    private TextView tv_rcvgoodname;
    private TextView tv_rcvgoodnum;
    private ImageView iv_rcvorderimg;

    public RcvOrderHolder(ViewGroup parent) {
        super(parent, R.layout.holder_rcvorder);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        tv_touser = findViewById(R.id.name);
        tv_touserphone = findViewById(R.id.phone);
        tv_touseraddress = findViewById(R.id.address);
        tv_totalprice = findViewById(R.id.totalprice);
        tv_rcvtime = findViewById(R.id.time);
        tv_rcvgoodname = findViewById(R.id.goodname);
        tv_rcvgoodnum = findViewById(R.id.goodnum);
        iv_rcvorderimg = findViewById(R.id.image);
    }

    @Override
    public void setData(RcvOrder object) {
        super.setData(object);
        tv_touser.setText(object.touser);
        tv_touserphone.setText(object.touserphone);
        tv_touseraddress.setText(object.touseraddress);
        tv_totalprice.setText(object.totalprice);
        tv_rcvtime.setText(object.rcvtime);
        tv_rcvgoodname.setText(object.rcvgoodname);
        tv_rcvgoodnum.setText("商品数量："+object.rcvgoodnum);

        Glide.with(itemView.getContext())
                .load(object.rcvorderimg)
                .into(iv_rcvorderimg);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
