package com.cateringpartner.cyhb.basket;

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

public class BasketHolder extends BaseViewHolder<Baskets> implements View.OnClickListener,View.OnLongClickListener {

    private TextView tv_company;
    private TextView tv_goodname;
    private TextView tv_totalprice;
    private TextView tv_goodnum;
    private TextView tv_time;
    private ImageView iv_image;

    public BasketHolder(ViewGroup parent) {
        super(parent, R.layout.holder_basket);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        tv_company = findViewById(R.id.company);
        tv_goodname = findViewById(R.id.goodname);
        tv_totalprice = findViewById(R.id.totalprice);
        tv_goodnum = findViewById(R.id.goodnum);
        tv_time = findViewById(R.id.time);
        iv_image = findViewById(R.id.image);
    }

    @Override
    public void setData(Baskets object) {
        super.setData(object);
        tv_company.setText(object.company);
        tv_goodname.setText(object.goodname);
        tv_totalprice.setText(object.totalprice);
        tv_goodnum.setText(object.goodnum);
        tv_time.setText(object.time);

        Glide.with(itemView.getContext())
                .load(object.image)
                .into(iv_image);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
