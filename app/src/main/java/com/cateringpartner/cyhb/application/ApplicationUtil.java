package com.cateringpartner.cyhb.application;

import android.app.Application;

/**
 * Created by AniChikage on 2017/8/7.
 */

public class ApplicationUtil extends Application{

    //全局变量
    private String uid; //用户ID
    private String activityFlag; //activity标志：当前是哪个界面，用于recyclerview判断

    public void setUid(String uid){this.uid = uid;}

    public String getUid(){return this.uid;}

    public void setActivityFlag(String activityFlag){this.activityFlag = activityFlag;}

    public String getActivityFlag(){return this.activityFlag;}
}
