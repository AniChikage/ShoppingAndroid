package com.cateringpartner.cyhb.specificCategory;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class AllGoods {

    public String imgsrc;
    public String goodname;
    public String introduction;
    public String goodprice;
    public String comment;
    public String ordernum;

    public AllGoods(String imgsrc, String goodname,String introduction,String goodprice,String comment, String ordernum) {
        this.imgsrc = imgsrc;
        this.goodname = goodname;
        this.introduction = introduction;
        this.goodprice = goodprice;
        this.comment = comment;
        this.ordernum = ordernum;
    }
}
