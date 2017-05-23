package com.hanrui.android.criminallntent;



import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author
 * @version 1.0
 * @date 2017/5/22 0022
 */
//单例对象
public class CrimeLab {
    //静态变量
    private static CrimeLab sCrimeLab;
    
    private List<Crime>mCrimes;
    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }
    
    private CrimeLab(Context context){
        mCrimes=new ArrayList<>();
        for(int i=0;i<100;i++){
            Crime crime=new Crime();
            crime.setTitle("Crime#"+i);
            crime.setSolved(i%2==0);
            mCrimes.add(crime);
        }
    }
    
    //返回数组列表
    public List<Crime>getCrimes(){
        return mCrimes;
    }
    //返回带有指定ID的Crime对象
    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
    
    
}
