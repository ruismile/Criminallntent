package com.hanrui.android.criminallntent;

import android.database.DatabaseErrorHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author
 * @version 1.0
 * @date 2017/5/22 0022
 */
//创建Crime实例，表示办公室陋习
public class Crime {
    private UUID mId;//随机产生一个id，重复性是没有的
    private String mTitle;//标题
    private Date mDate;//日期
    private boolean mSolved;//事情是否解决
    private String mSuspect;//嫌疑人姓名
    
    public Crime(){
        this(UUID.randomUUID());
    }
    public Crime(UUID id){
        mId=id;
        mDate=new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
    
    public String getSuspect(){
        return mSuspect;
    }
    
    public void setSuspect(String suspect){
        mSuspect=suspect;
    }
    
    //获取文件名
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
