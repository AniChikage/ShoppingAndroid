package com.cateringpartner.cyhb.basket;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class Baskets {

    public String company;
    public String goodname;
    public String totalprice;
    public String goodnum;
    public String time;
    public String image;

    public Baskets(String company, String goodname, String totalprice, String image,String goodnum,String time) {
        this.company = company;
        this.goodname = goodname;
        this.totalprice = totalprice;
        this.goodnum = goodnum;
        this.time = time;
        this.image = image;
    }
}
