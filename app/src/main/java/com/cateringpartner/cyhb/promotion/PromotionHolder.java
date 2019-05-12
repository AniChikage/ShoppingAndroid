package com.cateringpartner.cyhb.promotion;

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

public class PromotionHolder extends BaseViewHolder<Promotions> implements View.OnClickListener,View.OnLongClickListener {

    private TextView pcompany;
    private TextView pmain;
    private TextView pcompanyid;
    private ImageView pimage;

    public PromotionHolder(ViewGroup parent) {
        super(parent, R.layout.holder_promotion);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        pcompany = findViewById(R.id.company);
        pmain = findViewById(R.id.pmain);
        pcompanyid = findViewById(R.id.companyid);
        pimage = findViewById(R.id.image);
    }

    @Override
    public void setData(Promotions object) {
        super.setData(object);
        pcompany.setText(object.pcompany);
        pmain.setText(object.pmain);
        pcompanyid.setText(object.pcompanyid);

        Glide.with(itemView.getContext())
                .load(object.pimg)
                .into(pimage);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
