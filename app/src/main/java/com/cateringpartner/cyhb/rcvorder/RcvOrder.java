package com.cateringpartner.cyhb.rcvorder;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class RcvOrder {

    public String touser;
    public String touserphone;
    public String touseraddress;
    public String totalprice;
    public String rcvtime;
    public String rcvorderimg;
    public String rcvgoodname;
    public String rcvgoodnum;

    public RcvOrder(String touser, String touserphone, String touseraddress, String totalprice, String rcvtime, String rcvorderimg,String rcvgoodname,String rcvgoodnum) {
        this.touser = touser;
        this.touserphone = touserphone;
        this.touseraddress = touseraddress;
        this.totalprice = totalprice;
        this.rcvtime = rcvtime;
        this.rcvorderimg = rcvorderimg;
        this.rcvgoodname = rcvgoodname;
        this.rcvgoodnum = rcvgoodnum;
    }
}
