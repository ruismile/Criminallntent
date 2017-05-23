package com.hanrui.android.criminallntent;

import android.support.v4.app.Fragment;

/**
 * @author
 * @version 1.0
 * @date 2017/5/23 0023
 */

public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
