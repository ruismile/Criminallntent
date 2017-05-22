package com.hanrui.android.criminallntent;

import java.util.UUID;

/**
 * @author
 * @version 1.0
 * @date 2017/5/22 0022
 */
//创建Crime实例，表示办公室陋习
public class Crime {
    private UUID mId;//随机产生一个id，重复性是没有的
    private String mTitle;
    
    public Crime(){
        mId=UUID.randomUUID();
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
}
