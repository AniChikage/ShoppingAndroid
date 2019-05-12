package com.cateringpartner.cyhb.basket;

/**
 * Created by AniChikage on 2017/8/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.afollestad.dragselectrecyclerview.IDragSelectAdapter;
import com.bumptech.glide.Glide;
import com.cateringpartner.cyhb.R;
import com.cateringpartner.cyhb.fetchdata.FetchData;
import com.cateringpartner.cyhb.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** @author Aidan Follestad (afollestad) */
class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.MainViewHolder>
        implements IDragSelectAdapter {

    private List<Integer> selectedIndices;
    private Util util;
    private String[] goodnames;
    private String[] goodimgs;
    private String[] goodsingleprices;
    private String[] goodgoodnums;
    private String[] goodtotalprices;
    private String[] goodbasketgoodid;
    private boolean isLoadingOver = false;
    private Context context;
    private Activity activity;


    //private static final String[] ALPHABET =
    //        "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
    private static final int[] COLORS =
            new int[] {
                    Color.parseColor("#F44336"),
                    Color.parseColor("#E91E63"),
                    Color.parseColor("#9C27B0"),
                    Color.parseColor("#673AB7"),
                    Color.parseColor("#3F51B5"),
                    Color.parseColor("#2196F3"),
                    Color.parseColor("#03A9F4"),
                    Color.parseColor("#00BCD4"),
                    Color.parseColor("#009688"),
                    Color.parseColor("#4CAF50"),
                    Color.parseColor("#8BC34A"),
                    Color.parseColor("#CDDC39"),
                    Color.parseColor("#FFEB3B"),
                    Color.parseColor("#FFC107"),
                    Color.parseColor("#FF9800"),
                    Color.parseColor("#FF5722"),
                    Color.parseColor("#795548"),
                    Color.parseColor("#9E9E9E"),
                    Color.parseColor("#607D8B"),
                    Color.parseColor("#F44336"),
                    Color.parseColor("#E91E63"),
                    Color.parseColor("#9C27B0"),
                    Color.parseColor("#673AB7"),
                    Color.parseColor("#3F51B5"),
                    Color.parseColor("#2196F3"),
                    Color.parseColor("#03A9F4")
            };

    interface Listener {
        void onClick(int index);

        void onLongClick(int index);

        void onSelectionChanged(int count);
    }

    private final Listener callback;

    BasketAdapter(Listener callback, Activity activity) {
        super();
        util = new Util();
        this.selectedIndices = new ArrayList<>(util.getGoodnames().length);
        this.callback = callback;
        this.activity = activity;
        //new Thread(initDataRunnable).start();
    }

    //region
    private Runnable initDataRunnable = new Runnable() {
        @Override
        public void run() {
            String result;
            FetchData fetchData = new FetchData();
            result = fetchData.getBasket(util.getUserphone(), "0");
            Log.e("ddddddddddddddd", result);
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
                Log.e("ss",arr.length()+"");

                goodnames = new String[arr.length()];
                goodimgs = new String[arr.length()];
                goodsingleprices = new String[arr.length()];
                goodgoodnums = new String[arr.length()];
                goodtotalprices = new String[arr.length()];
                for(int i=0;i<arr.length();++i){
                    JSONObject jsonObject = (JSONObject)arr.get(i);
                    goodnames[i] = jsonObject.getJSONObject("allGoods").getString("goodname");
                    goodimgs[i] = jsonObject.getJSONObject("allGoods").getString("img");
                    goodsingleprices[i] = jsonObject.getJSONObject("allGoods").getString("goodprice");
                    goodtotalprices[i] = jsonObject.getJSONObject("orders").getString("totalprice");
                    goodgoodnums[i] = jsonObject.getJSONObject("orders").getString("goodnum");
                    goodbasketgoodid[i] = jsonObject.getJSONObject("orders").getString("oid");
                }
                isLoadingOver = true;
            } catch (Exception ex) {
                Log.e("roor", ex.toString());
            }
        }
    };
    //endregion

    String getItem(int index) {
        return util.getGoodbasketorderid()[index];
    }

    String getOid(int index) {
        return util.getGoodbasketorderid()[index];
    }

    String getPrice(int index) {
        return util.getGoodtotalprices()[index];
    }

    List<Integer> getSelectedIndices() {
        return selectedIndices;
    }

    void toggleSelected(int index) {
        if (selectedIndices.contains(index)) {
            selectedIndices.remove((Integer) index);
        } else {
            selectedIndices.add(index);
        }
        notifyItemChanged(index);
        if (callback != null) {
            callback.onSelectionChanged(selectedIndices.size());
        }
    }

    void clearSelected() {
        if (selectedIndices.isEmpty()) {
            return;
        }
        selectedIndices.clear();
        notifyDataSetChanged();
        if (callback != null) {
            callback.onSelectionChanged(0);
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.griditem_main, parent, false);
        return new MainViewHolder(v, callback);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        /*
        while (true){
            if(isLoadingOver){
                break;
            }
        }
        */
        Log.e("555555555555555555555",util.getGoodnames()[position]);
        holder.label.setText(util.getGoodbasketorderid()[position]);
        holder.tv_goodname.setText(util.getGoodnames()[position]);
        holder.tv_goodsingleprice.setText("单价："+util.getGoodsingleprices()[position]);
        holder.tv_goodtotalprice.setText("总价："+util.getGoodtotalprices()[position]);
        holder.tv_goodnum.setText("商品数量："+util.getGoodgoodnums()[position]);
        Glide.with(activity)
                .load(util.getGoodimgs()[position])
                .into(holder.iv_goodimg);

        final Drawable d;
        final Context c = holder.itemView.getContext();


        if (selectedIndices.contains(position)) {
            d = new ColorDrawable(ContextCompat.getColor(c, R.color.grid_foreground_selected));
            //holder.label.setTextColor(ContextCompat.getColor(c, R.color.grid_label_text_selected));
        } else {
            d = null;
            //holder.label.setTextColor(ContextCompat.getColor(c, R.color.grid_label_text_normal));
        }


        //noinspection RedundantCast
        ((FrameLayout) holder.colorSquare).setForeground(d);
        holder.colorSquare.setBackgroundColor(Color.parseColor("#F0F0F0"));
    }

    @Override
    public void setSelected(int index, boolean selected) {
        Log.d("BasketAdapter", "setSelected(" + index + ", " + selected + ")");
        if (!selected) {
            selectedIndices.remove((Integer) index);
        } else if (!selectedIndices.contains(index)) {
            selectedIndices.add(index);
        }
        notifyItemChanged(index);
        if (callback != null) {
            callback.onSelectionChanged(selectedIndices.size());
        }
    }

    @Override
    public boolean isIndexSelectable(int index) {
        return true;
    }

    @Override
    public int getItemCount() {
        return util.getGoodnames().length;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView label;
        private final TextView tv_goodname;
        private final TextView tv_goodsingleprice;
        private final TextView tv_goodnum;
        private final TextView tv_goodtotalprice;
        private final ImageView iv_goodimg;
        final RectangleView colorSquare;
        private final Listener callback;

        MainViewHolder(View itemView, Listener callback) {
            super(itemView);
            this.callback = callback;
            this.label = (TextView) itemView.findViewById(R.id.label);
            this.tv_goodname = (TextView) itemView.findViewById(R.id.goodname);
            this.tv_goodsingleprice = (TextView) itemView.findViewById(R.id.goodsingleprice);
            this.tv_goodnum = (TextView) itemView.findViewById(R.id.goodnum);
            this.tv_goodtotalprice = (TextView) itemView.findViewById(R.id.goodtotalprice);
            this.colorSquare = (RectangleView) itemView.findViewById(R.id.colorSquare);
            this.iv_goodimg = (ImageView) itemView.findViewById(R.id.goodimg);
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (callback != null) {
                callback.onClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (callback != null) {
                callback.onLongClick(getAdapterPosition());
            }
            return true;
        }
    }
}
