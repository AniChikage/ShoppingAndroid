package com.cateringpartner.cyhb.specificCategory;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.adapter.BaseViewHolder;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class SpecificGoodsHolder extends BaseViewHolder<AllGoods> implements View.OnClickListener,View.OnLongClickListener {

    private ImageView mImage;
    private TextView name;
    private TextView introduction;
    private TextView price;
    private TextView comment;
    private TextView ordernum;
    private MaterialRatingBar ratingBar;

    public SpecificGoodsHolder(ViewGroup parent) {
        super(parent, R.layout.specificgoodsholder);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        mImage = findViewById(R.id.image);
        name = findViewById(R.id.name);
        introduction = findViewById(R.id.introduction);
        price = findViewById(R.id.price);
        comment = findViewById(R.id.comment);
        ordernum = findViewById(R.id.ordernum);
        ratingBar = findViewById(R.id.ratingbar);
    }

    @Override
    public void setData(AllGoods object) {
        super.setData(object);
        name.setText(object.goodname);
        introduction.setText(object.introduction);
        price.setText(object.goodprice);
        comment.setText(object.comment);
        ordernum.setText("累计销量："+object.ordernum);
        //ratingBar.setNumStars(Integer.valueOf(object.comment));
        if(object.comment.equals("暂无评价") || object.comment.equals("暂无评分")){
            comment.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
        }
        else{
            ratingBar.setRating(Float.valueOf(object.comment));
            comment.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
        }

        Glide.with(itemView.getContext())
                .load(object.imgsrc)
                .into(mImage);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
