package com.cateringpartner.cyhb.goodDetail;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.adapter.BaseViewHolder;

/**
 * Created by linlongxin on 2016/8/23.
 */

public class CommentsHolder extends BaseViewHolder<Comments> implements View.OnClickListener,View.OnLongClickListener {

    private TextView phone;
    private TextView rank;
    private TextView comment;

    public CommentsHolder(ViewGroup parent) {
        super(parent, R.layout.comment);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        phone = findViewById(R.id.phone);
        rank = findViewById(R.id.rank);
        comment = findViewById(R.id.comment);
    }

    @Override
    public void setData(Comments object) {
        super.setData(object);
        phone.setText(object.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        rank.setText("评分: "+object.rank);
        comment.setText(object.comment);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
