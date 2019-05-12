package com.cateringpartner.cyhb.promotion;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class Promotions {

    public String pcompany;
    public String pmain;
    public String pimg;
    public String pcompanyid;

    public Promotions(String pcompanyid, String pcompany, String pmain, String pimg) {
        this.pcompanyid = pcompanyid;
        this.pcompany = pcompany;
        this.pmain = pmain;
        this.pimg = pimg;
    }
}
