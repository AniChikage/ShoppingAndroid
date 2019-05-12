package com.cateringpartner.cyhb.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.adapter.BaseViewHolder;
import com.cateringpartner.cyhb.bean.Category;

/**
 * Created by linlongxin on 2016/8/23.
 */

public class CategoryHolder extends BaseViewHolder<Category> implements View.OnClickListener,View.OnLongClickListener {

    private TextView mText;
    private ImageView mImage;

    public CategoryHolder(ViewGroup parent) {
        super(parent, R.layout.holder_category);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        mText = findViewById(R.id.text);
        mImage = findViewById(R.id.image);
    }

    @Override
    public void setData(Category object) {
        super.setData(object);
        mText.setText(object.text);

        Glide.with(itemView.getContext())
                .load(object.image)
                .into(mImage);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemViewClick(Category object) {
        super.onItemViewClick(object);
        //点击事件
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
