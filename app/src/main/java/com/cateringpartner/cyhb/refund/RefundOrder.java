package com.cateringpartner.cyhb.refund;

/**
 * Created by AniChikage on 2017/6/4.
 */

public class RefundOrder {

    public String touser;
    public String touserphone;
    public String touseralipayaccount;
    public String totalprice;
    public String rcvtime;
    public String rcvorderimg;
    public String rcvgoodname;
    public String refundreason;

    public RefundOrder(String touser, String touserphone, String touseralipayaccount, String totalprice, String rcvtime, String rcvorderimg,String rcvgoodname,String refundreason) {
        this.touser = touser;
        this.touserphone = touserphone;
        this.touseralipayaccount = touseralipayaccount;
        this.totalprice = totalprice;
        this.rcvtime = rcvtime;
        this.rcvorderimg = rcvorderimg;
        this.rcvgoodname = rcvgoodname;
        this.refundreason = refundreason;
        this.refundreason = refundreason;
    }
}
