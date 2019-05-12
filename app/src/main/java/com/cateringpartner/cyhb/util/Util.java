package com.cateringpartner.cyhb.util;

/**
 * Created by AniChikage on 2017/6/4.
 */

/*
 1.首页
 2.购物车
 3.推广
 4.我的订单
 5.我
 6.具体哪一类商品

 */

public class Util {

    public static int activityFlag;
    public static int preActivityFlag; //0:homeragment;1:specific category
    public static int preGoodDetailActivityFlag; //0:homeragment;1:specific category
    public static String[] goodsGroup;
    public static String[] goodsGroupShop;
    public static String[] preGoodsGroup;
    public static String userphone;
    public static String uid;
    public static Integer[] basketOrderGroup;
    public static Integer[] BANNERIDGROUP;
    public static Integer[] GlobalPromotionId;
    public static String GLOBALCOMPANY;
    public static String GLOBALCOMPANYID;
    public static String SEARCHEDGOODNAME;
    public static String GLOBALUSERID;
    public static String GLOBALORDERID;

    public static String[] goodnames;
    public static String[] goodimgs;
    public static String[] goodsingleprices;
    public static String[] goodgoodnums;
    public static String[] goodtotalprices;
    public static String[] goodbasketorderid;
    public static String[] topayorderids;
    public static String totalprice;

    public static String delFlag;


    public void setActivityFlag(int activityFlag){
        this.activityFlag = activityFlag;
    }

    public int getActivityFlag(){
        return this.activityFlag;
    }

    public void setGoodsGroup(String[] goodsGroup){this.goodsGroup = goodsGroup;}

    public String[] getGoodsGroup(){
        return this.goodsGroup;
    }

    public String getGoodsID(int index){
        return this.goodsGroup[index];
    }

    public void setUserphone(String userphone){this.userphone = userphone;}

    public String getUserphone(){return this.userphone;}

    public void setBasketOrderGroup(Integer[] basketOrderGroup){this.basketOrderGroup = basketOrderGroup;}

    public Integer[] getBasketOrderGroup(){return this.basketOrderGroup;}

    public Integer getBasketOrderGroupByIndex(int index){
        return this.basketOrderGroup[index];
    }

    public String getUid(){return this.uid;}

    public void setUid(String uid){this.uid = uid;}

    public String getGLOBALCOMPANY(){return this.GLOBALCOMPANY;}

    public void setGLOBALCOMPANY(String GLOBALCOMPANY){this.GLOBALCOMPANY = GLOBALCOMPANY;}

    public String getSEARCHEDGOODNAME(){return this.SEARCHEDGOODNAME;}

    public void setSEARCHEDGOODNAME(String SEARCHEDGOODNAME){this.SEARCHEDGOODNAME = SEARCHEDGOODNAME;}

    public String getGLOBALUSERID(){return this.GLOBALUSERID;}

    public void setGLOBALUSERID(String GLOBALUSERID){this.GLOBALUSERID = GLOBALUSERID;}

    public String getGLOBALCOMPANYID(){return this.GLOBALCOMPANYID;}

    public void setGLOBALCOMPANYID(String GLOBALCOMPANYID){this.GLOBALCOMPANYID = GLOBALCOMPANYID;}

    public String getGLOBALORDERID(){return this.GLOBALORDERID;}

    public void setGLOBALORDERID(String GLOBALORDERID){this.GLOBALORDERID = GLOBALORDERID;}

    public Integer[] getBANNERIDGROUP(){return this.BANNERIDGROUP;}

    public void setBANNERIDGROUP(Integer[] BANNERIDGROUP){this.BANNERIDGROUP = BANNERIDGROUP;}

    public Integer[] getGlobalPromotionId(){return this.GlobalPromotionId;}

    public void setGlobalPromotionId(Integer[] GlobalPromotionId){this.GlobalPromotionId = GlobalPromotionId;}

    public void setGoodnames(String[] goodnames){this.goodnames = goodnames;}

    public String[] getGoodnames(){return this.goodnames;}

    public void setGoodimgs(String[] goodimgs){this.goodimgs = goodimgs;}

    public String[] getGoodimgs(){return this.goodimgs;}

    public void setGoodsingleprices(String[] goodsingleprices){this.goodsingleprices = goodsingleprices;}

    public String[] getGoodsingleprices(){return this.goodsingleprices;}

    public void setGoodgoodnums(String[] goodgoodnums){this.goodgoodnums = goodgoodnums;}

    public String[] getGoodgoodnums(){return this.goodgoodnums;}

    public void setGoodtotalprices(String[] goodtotalprices){this.goodtotalprices = goodtotalprices;}

    public String[] getGoodtotalprices(){return this.goodtotalprices;}

    public void setGoodbasketorderid(String[] goodbasketorderid){this.goodbasketorderid = goodbasketorderid;}

    public String[] getGoodbasketorderid(){return this.goodbasketorderid;}

    public void setTopayorderids(String[] topayorderids){this.topayorderids = topayorderids;}

    public String[] getTopayorderids(){return this.topayorderids;}

    public void setTotalprice(String totalprice){this.totalprice = totalprice;}

    public String getTotalprice(){return this.totalprice;}

    public void setPreActivityFlag(int preActivityFlag){this.preActivityFlag = preActivityFlag;}

    public int getPreActivityFlag(){return this.preActivityFlag;}

    public void setDelFlag(String delFlag){this.delFlag = delFlag;}

    public String getDelFlag(){return this.delFlag;}

    public void setPreGoodsGroup(String[] preGoodsGroup){this.preGoodsGroup = preGoodsGroup;}

    public String[] getPreGoodsGroup(){return this.preGoodsGroup;}

    public void setGoodsGroupShop(String[] goodsGroupShop){this.goodsGroupShop = goodsGroupShop;}

    public String[] getGoodsGroupShop(){return this.goodsGroupShop;}

    public String getGoodsIDShop(int index){return this.goodsGroupShop[index];}

    public void setPreGoodDetailActivityFlag(int preGoodDetailActivityFlag){this.preGoodDetailActivityFlag = preGoodDetailActivityFlag;}

    public int getPreGoodDetailActivityFlag(){return this.preGoodDetailActivityFlag;}
}
