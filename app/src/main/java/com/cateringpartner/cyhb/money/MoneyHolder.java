package com.cateringpartner.cyhb.money;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.adapter.BaseViewHolder;

/**
 * Created by linlongxin on 2016/8/23.
 */

public class MoneyHolder extends BaseViewHolder<Money> implements View.OnClickListener,View.OnLongClickListener {

    private TextView tv_company;
    private TextView tv_realname;
    private TextView tv_phone;
    private TextView tv_alipayaccount;
    private TextView tv_price;

    public MoneyHolder(ViewGroup parent) {
        super(parent, R.layout.holder_money);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        tv_company = findViewById(R.id.company);
        tv_alipayaccount = findViewById(R.id.alipayaccount);
        tv_realname = findViewById(R.id.realname);
        tv_phone = findViewById(R.id.phone);
        tv_price = findViewById(R.id.price);
    }

    @Override
    public void setData(Money object) {
        super.setData(object);
        tv_company.setText(object.company);
        tv_alipayaccount.setText(object.alipayaccount);
        tv_realname.setText(object.realname);
        tv_phone.setText(object.phone);
        tv_price.setText(object.price);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
