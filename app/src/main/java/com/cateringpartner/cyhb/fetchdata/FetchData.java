package com.cateringpartner.cyhb.fetchdata;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by AniChikage on 2017/5/25.
 */

public class FetchData {
    //private String login_url = "http://47.92.74.241:8080/login/getPhoneAndPassword";
    private String login_url = "http://47.92.74.241:8080/login/check";
    private String loginByPhoneOrUsername_url = "http://47.92.74.241:8080/login/check";
    private String checkPhone_url = "http://47.92.74.241:8080/login/getPhone";
    private String testPost_url = "http://47.92.74.241:8080/uploadportrait";
    //private String getTypeCategory_url = "http://47.92.74.241:8080/getCateGoodsByType";
    private String getTypeCategory_url = "http://47.92.74.241:8080/admin/app/getGoodsByItemAndSubitemAndCity";
    private String getPromotionsByPage_url = "http://47.92.74.241:8080/getPromotionsByPage";
    private String getGoodDetail_url = "http://47.92.74.241:8080/getGoodDetail";
    private String addOrder_url = "http://47.92.74.241:8080/addOrder";
    private String getBasket_url = "http://47.92.74.241:8080/getBasket";
    private String getBasketByOid_url = "http://47.92.74.241:8080/getBasketByOid";
    private String getUserByPhone_url = "http://47.92.74.241:8080/getUserByPhone";
    private String getUserByUid_url = "http://47.92.74.241:8080/getUserByUid";
    private String delBasketOrder_url = "http://47.92.74.241:8080/delBasketOrder";
    private String confirmOrder_url = "http://47.92.74.241:8080/confirmOrder";
    private String commentOrder_url = "http://47.92.74.241:8080/commentOrder";
    private String addComment_url = "http://47.92.74.241:8080/addComment";
    private String getCommentByGoodid_url = "http://47.92.74.241:8080/getCommentByGoodid";

    private String registerUser_url = "http://47.92.74.241:8080/register/goregister";
    private String getOrderByCompany_url = "http://47.92.74.241:8080/getOrderByCompany";
    private String UserAddUser_url = "http://47.92.74.241:8080/login/adduser";

    //GOOD
    //private String GoodSearchGood_url = "http://47.92.74.241:8080/good/search";
    private String GoodSearchGood_url = "http://47.92.74.241:8080/good/searchByCity";
    private String GoodSearchGoodByPrice_url = "http://47.92.74.241:8080/good/searchByCityByPrice";
    private String GoodGetGoodByCompany_url = "http://47.92.74.241:8080/good/getgoodbycompany";
    private String GoodGetGoodByCompanyid_url = "http://47.92.74.241:8080/good/getgoodbycompanyid";
    private String GoodGetGoodByCompanyidAndSearchname_url = "http://47.92.74.241:8080/good/getgoodbycompanyidandsearchname";
    private String GoodGetGoodByItemnameAndCityByZonghe_url = "http://47.92.74.241:8080/app/getGoodsByItemnameAndCityByZonehe";
    private String GoodGetGoodByItemnameAndCityByPrice_url = "http://47.92.74.241:8080/app/getGoodsItemAndSubitemByGoodpriceAndCity";
    private String GoodGetGoodByItemnameAndCityBySales_url = "http://47.92.74.241:8080/app/getGoodBySalesAndCity";
    private String GoodGetGoodByItemnameAndCityBySalesSearch_url = "http://47.92.74.241:8080/app/getGoodBySalesAndCityBySales";

    //ORDER
    private String OrderRefundOrder_url = "http://47.92.74.241:8080/orderapp/refundOrder";
    private String OrderRefundOrderApp_url = "http://47.92.74.241:8080/orderapp/refundOrderImg";
    private String OrderGetOrderByCompanyid_url = "http://47.92.74.241:8080/order/getOrderByCompanyid";
    private String OrderGetBasketByUid_url = "http://47.92.74.241:8080/getBasketByUidAndPaid";
    private String OrderSetOrderPaid_url = "http://47.92.74.241:8080/setOrderPaid";
    private String OrderGetOrderByUidAndPaid_url = "http://47.92.74.241:8080/getBasketByUidAndPaid";
    private String OrderGetOrderByUidAndPaidToday_url = "http://47.92.74.241:8080/getBasketByUidAndPaidToday";
    private String OrderGetRefundOrder_url = "http://47.92.74.241:8080/admin/getRefundOrder";
    private String OrderGetShopOrderToday_url = "http://47.92.74.241:8080/shop/getShopOrderToday";
    private String OrderGetShopRefundOrder_url = "http://47.92.74.241:8080/shop/getRefundOrder";
    private String OrderDelOrder_url = "http://47.92.74.241:8080/admin/delOrder";

    //PROMOTION
    private String UserGetPromotion_url = "http://47.92.74.241:8080/promotion/getPromotion";

    //USER
    private String UserGetUserByUid_url = "http://47.92.74.241:8080/user/getUserByUid";
    private String UserResetUserPassword_url = "http://47.92.74.241:8080/login/resetPassword";
    private String UserResetUserPasswordByUid_url = "http://47.92.74.241:8080/login/resetPasswordByUid";
    private String UserUpdateUser_url = "http://47.92.74.241:8080/userapp/updateUserInfo";

    //ADMIN
    private String AdminGetMoney_url = "http://47.92.74.241:8080/admin/getMoney";
    private String AdminMoneyDetail_url = "http://47.92.74.241:8080/admin/getMoneyDetail";
    private String AdminSales_url = "http://47.92.74.241:8080/admin/getSales";
    private String AdminGetOrderToday = "http://47.92.74.241:8080/admin/getAllOrderToday";

    //MONEY
    private String ShopGetMoney_url = "http://47.92.74.241:8080/shop/getShopMoney";

    //COMMENT
    private String CommentGetCommentByGoodid_url = "http://47.92.74.241:8080/getCommentByGoodid";
    private String CommentAddComment_url = "http://47.92.74.241:8080/admin/addComment";
    private String CommentAddCommentApp_url = "http://47.92.74.241:8080/adminapp/addComment";

    //VERSION
    private String VersionGetVersion_url = "http://47.92.74.241:8080/admin/getWebVersion";

    ///--------------------------------------------------------------------------------------------------------------------------------------

    public String doCheckPhone(String phone){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            checkPhone_url += "?";
            checkPhone_url = checkPhone_url + "phone=" + phone;
            URL url = new URL(checkPhone_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String getVersion(){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            URL url = new URL(VersionGetVersion_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String doLogin(String phone, String password){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            login_url += "?";
            login_url = login_url + "param=" + URLEncoder.encode(phone,"utf-8") + "&";
            login_url = login_url + "password=" + URLEncoder.encode(password,"utf-8");
            URL url = new URL(login_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String getWebVersion(){
        String result = "";
        try{
            /*
            URL url = new URL("http://47.92.74.241:80/version.txt");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setReadTimeout(8 * 1000);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String string = bufferedReader.readLine();
            //对json数据进行解析
            JSONObject jsonObject = new JSONObject(string);
            String strings = jsonObject.getString("code");

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            login_url += "?";
            login_url = login_url + "param=" + URLEncoder.encode(phone,"utf-8") + "&";
            login_url = login_url + "password=" + URLEncoder.encode(password,"utf-8");
            */
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            URL url = new URL("http://47.92.74.241:80/version.txt");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_doLogin(String param, String password){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            loginByPhoneOrUsername_url += "?";
            loginByPhoneOrUsername_url = loginByPhoneOrUsername_url + "param=" + param + "&";
            loginByPhoneOrUsername_url = loginByPhoneOrUsername_url + "password=" + password;
            URL url = new URL(login_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String postImg(String imgstr){
        String result = "";

        return result;
    }

    public String getSpecificCateGoods(String gooditemname,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[102400];
            int len = 0;
            String url1 = GoodGetGoodByItemnameAndCityByZonghe_url;
            url1 += "?";
            url1 += "gooditemname=" + URLEncoder.encode(gooditemname, "utf-8") + "&";
            url1 += "uid=" + URLEncoder.encode(uid, "utf-8");
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String getSpecificCateGoodsBySales(String gooditemname,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[102400];
            int len = 0;
            String url1 = GoodGetGoodByItemnameAndCityBySales_url;
            url1 += "?";
            url1 += "gooditemname=" + URLEncoder.encode(gooditemname, "utf-8") + "&";
            url1 += "uid=" + URLEncoder.encode(uid, "utf-8");
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String getSpecificCateGoodsBySalesSearch(String key,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[102400];
            int len = 0;
            String url1 = GoodGetGoodByItemnameAndCityBySalesSearch_url;
            url1 += "?";
            url1 += "key=" + URLEncoder.encode(key, "utf-8") + "&";
            url1 += "uid=" + URLEncoder.encode(uid, "utf-8");
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String getSpecificCateGoodsByPrice(String gooditemname,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[102400];
            int len = 0;
            String url1 = GoodGetGoodByItemnameAndCityByPrice_url;
            url1 += "?";
            url1 += "gooditemname=" + URLEncoder.encode(gooditemname, "utf-8") + "&";
            url1 += "uid=" + URLEncoder.encode(uid, "utf-8");
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //
    public String getGoodDetail(String goodid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            getGoodDetail_url += "?";
            getGoodDetail_url = getGoodDetail_url + "goodid=" + goodid;
            URL url = new URL(getGoodDetail_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //getpromotionsbypage
    public String getPromotionsByPage(String ppage){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            getPromotionsByPage_url += "?";
            getPromotionsByPage_url = getPromotionsByPage_url + "ppage=" + ppage;
            URL url = new URL(getPromotionsByPage_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //getpromotionsbypage
    public String addOrder(String goodid, String goodname,String uid, String phone, String totalprice,String sellprice, String deliveryprice, String paid, String refund,String refundaccept, String createtime,String companyid, String company, String rcvconfirm, String iscomment,String goodnum){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            addOrder_url += "?";
            addOrder_url = addOrder_url + "goodid=" + goodid + "&";
            addOrder_url = addOrder_url + "goodname=" + URLEncoder.encode(goodname, "utf-8") + "&";
            addOrder_url = addOrder_url + "uid=" + URLEncoder.encode(uid, "utf-8") + "&";
            addOrder_url = addOrder_url + "phone=" + phone + "&";
            addOrder_url = addOrder_url + "totalprice=" + totalprice + "&";
            addOrder_url = addOrder_url + "sellprice=" + sellprice + "&";
            addOrder_url = addOrder_url + "deliveryprice=" + deliveryprice + "&";
            addOrder_url = addOrder_url + "paid=" + paid + "&";
            addOrder_url = addOrder_url + "refund=" + refund + "&";
            addOrder_url = addOrder_url + "refundaccespt=" + refundaccept + "&";
            addOrder_url = addOrder_url + "createtime=" + createtime + "&";
            addOrder_url = addOrder_url + "companyid=" + companyid + "&";
            addOrder_url = addOrder_url + "company=" + URLEncoder.encode(company, "utf-8") + "&";
            addOrder_url = addOrder_url + "rcvconfirm=" + rcvconfirm + "&";
            addOrder_url = addOrder_url + "iscomment=" + iscomment + "&";
            addOrder_url = addOrder_url + "goodnum=" + goodnum;
            addOrder_url.replaceAll(" ","%20");
            URL url = new URL(addOrder_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //getpromotionsbypage
    public String getBasket(String phone, String paid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            getBasket_url += "?";
            getBasket_url = getBasket_url + "phone=" + phone + "&";
            getBasket_url = getBasket_url + "paid=" + paid;
            URL url = new URL(getBasket_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String shopGetShopMoney(String shopid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            ShopGetMoney_url += "?";
            ShopGetMoney_url = ShopGetMoney_url + "shopid="+shopid;
            URL url = new URL(ShopGetMoney_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //get user by uid
    //getpromotionsbypage
    public String getUserByUid(String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            getUserByUid_url += "?";
            getUserByUid_url = getUserByUid_url + "uid=" + uid;
            URL url = new URL(getUserByUid_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //getpromotionsbypage
    public String getBasketByBasketId(String basketId){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            getBasketByOid_url += "?";
            getBasketByOid_url = getBasketByOid_url + "oid=" + basketId;
            URL url = new URL(getBasketByOid_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String BasketGetBasketByUid(String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String url1=OrderGetBasketByUid_url;
            url1 += "?";
            url1 = url1 + "uid=" + uid +"&paid=0";
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String setOrderPaid(String oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String url1=OrderSetOrderPaid_url;
            url1 += "?";
            url1 = url1 + "oid=" + oid;
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String getUserByPhone(String phone){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            getUserByPhone_url += "?";
            getUserByPhone_url = getUserByPhone_url + "phone=" + phone;
            URL url = new URL(getUserByPhone_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String delBasketOrder(String oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            delBasketOrder_url += "?";
            delBasketOrder_url = delBasketOrder_url + "oid=" + oid;
            URL url = new URL(delBasketOrder_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //region 确认收货
    public String confirmOrder(String oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            confirmOrder_url += "?";
            confirmOrder_url = confirmOrder_url + "rcvconfirm=" + 1 + "&";
            confirmOrder_url = confirmOrder_url + "oid=" + oid;
            URL url = new URL(confirmOrder_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //endreigon

    //region 确认评价
    public String commentOrder(String oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            commentOrder_url += "?";
            commentOrder_url = commentOrder_url + "iscomment=" + 1 + "&";
            commentOrder_url = commentOrder_url + "oid=" + oid;
            URL url = new URL(commentOrder_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //endreigon

    //region 获取商品评价
    public String getCommetByGoodid(String goodid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String url1=CommentGetCommentByGoodid_url;
            url1 += "?";
            url1 = url1 + "goodid=" + goodid;
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //region 确认评价
    public String updateUser(String phone,String username,String realname,String alipayaccount,String usercity,String district,String address,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl=UserUpdateUser_url;
            rawurl += "?";
            rawurl = rawurl + "phone=" + URLEncoder.encode(phone, "utf-8") + "&";
            rawurl = rawurl + "username=" + URLEncoder.encode(username, "utf-8") + "&";
            rawurl = rawurl + "realname=" + URLEncoder.encode(realname, "utf-8") + "&";
            rawurl = rawurl + "alipayaccount=" + URLEncoder.encode(alipayaccount, "utf-8") + "&";
            rawurl = rawurl + "usercity=" + URLEncoder.encode(usercity, "utf-8") + "&";
            rawurl = rawurl + "district=" + URLEncoder.encode(district, "utf-8") + "&";
            rawurl = rawurl + "address=" + URLEncoder.encode(address, "utf-8") + "&";
            rawurl = rawurl + "uid=" + URLEncoder.encode(uid, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //region 确认评价
    public String getOrderByCompany(String company){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            getOrderByCompany_url += "?";
            getOrderByCompany_url = getOrderByCompany_url + "company=" + URLEncoder.encode(company, "utf-8");
            URL url = new URL(getOrderByCompany_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //region 确认评价
    public String registerUser(String phone, String realname, String username, String usertype, String password, String email, String address){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            registerUser_url += "?";
            registerUser_url = registerUser_url + "phone=" + URLEncoder.encode(phone, "utf-8") + "&";
            registerUser_url = registerUser_url + "realname=" + URLEncoder.encode(realname, "utf-8") + "&";
            registerUser_url = registerUser_url + "username=" + URLEncoder.encode(username, "utf-8") + "&";
            registerUser_url = registerUser_url + "usertype=" + URLEncoder.encode(usertype, "utf-8") + "&";
            registerUser_url = registerUser_url + "password=" + URLEncoder.encode(password, "utf-8") + "&";
            registerUser_url = registerUser_url + "email=" + URLEncoder.encode(email, "utf-8") + "&";
            registerUser_url = registerUser_url + "address=" + URLEncoder.encode(address, "utf-8");
            URL url = new URL(registerUser_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //
    public String User_addUser(String username, String phone, String passowrd, String usertype, String realname, String usercity, String district, String address,String alipayaccount){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = UserAddUser_url;
            rawurl += "?";
            rawurl = rawurl + "username=" + URLEncoder.encode(username, "utf-8") + "&";
            rawurl = rawurl + "phone=" + URLEncoder.encode(phone, "utf-8") + "&";
            rawurl = rawurl + "password=" + URLEncoder.encode(passowrd, "utf-8") + "&";
            rawurl = rawurl + "usertype=" + URLEncoder.encode(usertype, "utf-8") + "&";
            rawurl = rawurl + "realname=" + URLEncoder.encode(realname, "utf-8") + "&";
            rawurl = rawurl + "usercity=" + URLEncoder.encode(usercity, "utf-8") + "&";
            rawurl = rawurl + "district=" + URLEncoder.encode(district, "utf-8") + "&";
            rawurl = rawurl + "alipayaccount=" + URLEncoder.encode(alipayaccount, "utf-8") + "&";
            rawurl = rawurl + "address=" + URLEncoder.encode(address, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }
    //
    public String User_addUserShopper(String username, String phone, String passowrd, String usertype, String realname, String usercity, String district, String address, String company,String cpmain, String cpcontent){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = UserAddUser_url;
            rawurl += "?";
            rawurl = rawurl + "username=" + URLEncoder.encode(username, "utf-8") + "&";
            rawurl = rawurl + "phone=" + URLEncoder.encode(phone, "utf-8") + "&";
            rawurl = rawurl + "password=" + URLEncoder.encode(passowrd, "utf-8") + "&";
            rawurl = rawurl + "usertype=" + URLEncoder.encode(usertype, "utf-8") + "&";
            rawurl = rawurl + "realname=" + URLEncoder.encode(realname, "utf-8") + "&";
            rawurl = rawurl + "usercity=" + URLEncoder.encode(usercity, "utf-8") + "&";
            rawurl = rawurl + "district=" + URLEncoder.encode(district, "utf-8") + "&";
            rawurl = rawurl + "company=" + URLEncoder.encode(company, "utf-8") + "&";
            rawurl = rawurl + "cpmain=" + URLEncoder.encode(cpmain, "utf-8") + "&";
            rawurl = rawurl + "cpcontent=" + URLEncoder.encode(cpcontent, "utf-8") + "&";
            rawurl = rawurl + "address=" + URLEncoder.encode(address, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Order_refundOrder(String refund, String refundaccount, String refundreason, Integer oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderRefundOrder_url;
            rawurl += "?";
            rawurl = rawurl + "refund=" + URLEncoder.encode(refund, "utf-8") + "&";
            rawurl = rawurl + "refundaccount=" + URLEncoder.encode(refundaccount, "utf-8") + "&";
            rawurl = rawurl + "refundreason=" + URLEncoder.encode(refundreason, "utf-8") + "&";
            rawurl = rawurl + "oid=" + oid;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERRORupload", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Order_refundOrderImg(String imgstr, Integer oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[409600];
            int len = 0;
            String rawurl = OrderRefundOrderApp_url;
            rawurl += "?";
            rawurl = rawurl + "imgstr=" + URLEncoder.encode(imgstr, "utf-8") + "&";
            rawurl = rawurl + "oid=" + oid;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERRORupload", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_GetPromotion(String promotion){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = UserGetPromotion_url;
            rawurl += "?";
            rawurl = rawurl + "promotion=" + URLEncoder.encode(promotion, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_getOrder(String uid,String paid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderGetOrderByUidAndPaid_url;
            rawurl += "?";
            rawurl = rawurl + "uid=" + URLEncoder.encode(uid, "utf-8") +"&";
            rawurl = rawurl + "paid=" + URLEncoder.encode(paid, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_getOrderToday(String uid,String paid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderGetOrderByUidAndPaidToday_url;
            rawurl += "?";
            rawurl = rawurl + "uid=" + URLEncoder.encode(uid, "utf-8") +"&";
            rawurl = rawurl + "paid=" + URLEncoder.encode(paid, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Admin_getRefundOrder(){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderGetRefundOrder_url;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Shop_getShopOrderToday(String shopid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderGetShopOrderToday_url;
            rawurl+="?shopid="+shopid;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Order_DelOrder(String oid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
                String rawurl = OrderDelOrder_url;
                rawurl+="?oid="+oid;
                URL url = new URL(rawurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Charset", "UTF-8");
                InputStream inStream = conn.getInputStream();
                while ((len = inStream.read(data)) != -1) {
                    outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Shop_getShopRefundOrderToday(String shopid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderGetShopRefundOrder_url;
            rawurl+="?companyid="+shopid;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_GetUserByUid(String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = UserGetUserByUid_url;
            rawurl += "?";
            rawurl = rawurl + "uid=" + URLEncoder.encode(uid, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_ResetPawword(String username){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = UserResetUserPassword_url;
            rawurl += "?";
            rawurl = rawurl + "username=" + URLEncoder.encode(username, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_ResetPawwordByUid(String uid, String password, String newpassword){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = UserResetUserPasswordByUid_url;
            rawurl += "?";
            rawurl = rawurl + "uid=" + URLEncoder.encode(uid, "utf-8") +"&";
            rawurl = rawurl + "password=" + URLEncoder.encode(password, "utf-8") +"&";
            rawurl = rawurl + "newpassword=" + URLEncoder.encode(newpassword, "utf-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //endreigon

    //region 提交评价
    public String addComment(String goodid, String phone, String rank, String comment){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            addComment_url += "?";
            addComment_url = addComment_url + "goodid=" + goodid + "&";
            addComment_url = addComment_url + "phone=" + phone + "&";
            addComment_url = addComment_url + "rank=" + rank + "&";
            addComment_url = addComment_url + "comment=" + URLEncoder.encode(comment, "utf-8");
            URL url = new URL(addComment_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    //endreigon


    /*
     * Function  :   封装请求体信息
     * Param     :   params请求体内容，encode编码格式
     * Author    :   博客园-依旧淡然
     */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     * Author    :   博客园-依旧淡然
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    //region GOOD
    public String Good_searchGood(String key,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = GoodSearchGood_url;
            rawurl += "?";
            rawurl = rawurl + "key=" + URLEncoder.encode(key,"UTF-8") + "&";
            rawurl = rawurl + "uid=" + uid;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Good_searchGoodByPrice(String key,String uid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = GoodSearchGoodByPrice_url;
            rawurl += "?";
            rawurl = rawurl + "key=" + URLEncoder.encode(key,"UTF-8") + "&";
            rawurl = rawurl + "uid=" + uid;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Good_getGoodByCompany(String company){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = GoodGetGoodByCompany_url;
            rawurl += "?";
            rawurl = rawurl + "company=" + URLEncoder.encode(company,"UTF-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Good_getGoodByCompanyid(String companyid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = GoodGetGoodByCompanyid_url;
            rawurl += "?";
            rawurl = rawurl + "companyid=" + URLEncoder.encode(companyid,"UTF-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Good_getGoodByCompanyidAndSearchname(String companyid, String searchname){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = GoodGetGoodByCompanyidAndSearchname_url;
            rawurl += "?";
            rawurl = rawurl + "companyid=" + URLEncoder.encode(companyid,"UTF-8")+"&";
            rawurl = rawurl + "searchname=" + URLEncoder.encode(searchname,"UTF-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Order_GetOrderByCompanyid(String companyid){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = OrderGetOrderByCompanyid_url;
            rawurl += "?";
            rawurl = rawurl + "companyid=" + URLEncoder.encode(companyid,"UTF-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Admin_GetMoney(){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = AdminGetMoney_url;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Admin_GetMoneyDetail(){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = AdminMoneyDetail_url;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Admin_GetSales(){
        String result = "";
        try{

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = AdminSales_url;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String Admin_GetOrderToday(){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int len = 0;
            String rawurl = AdminGetOrderToday;
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }

    public String User_AddComment(String goodid,String uid,String rank,String comment,String imgstr){
        String result = "";
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[409600];
            int len = 0;
            String rawurl = CommentAddCommentApp_url + "?";
            rawurl += "goodid="+URLEncoder.encode(goodid,"UTF-8") +"&";
            rawurl += "uid="+URLEncoder.encode(uid,"UTF-8") +"&";
            rawurl += "rank="+URLEncoder.encode(rank,"UTF-8") +"&";
            rawurl += "comment="+URLEncoder.encode(comment,"UTF-8")+"&";
            rawurl += "imgstr="+URLEncoder.encode(imgstr,"UTF-8");
            URL url = new URL(rawurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            InputStream inStream = conn.getInputStream();
            while ((len = inStream.read(data)) != -1) {
                outStream.write(data, 0, len);
            }
            inStream.close();
            result =  new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
        }
        catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }
        Log.e("result", result);
        return result;
    }
    //endregion
}
