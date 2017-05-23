package com.hanrui.android.criminallntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author
 * @version 1.0
 * @date 2017/5/23 0023
 */

public abstract class SingleFragmentActivity extends FragmentActivity{
    
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //获取FragmentManager
        FragmentManager fm=getSupportFragmentManager();
        //获取一个fragment并交由FragmentManager管理
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment=createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}
