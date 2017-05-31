package com.hanrui.android.criminallntent;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hanrui.android.criminallntent.database.CrimeBaseHelper;
import com.hanrui.android.criminallntent.database.CrimeCursorWrapper;
import com.hanrui.android.criminallntent.database.CrimeDbSchema;
import com.hanrui.android.criminallntent.database.CrimeDbSchema.CrimeTable;

import java.io.ObjectOutputStream;
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
    
    private Context mContext;
    private SQLiteDatabase mDatebase;
    
    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }
    
    private CrimeLab(Context context){
        mContext=context.getApplicationContext();
        mDatebase=new CrimeBaseHelper(mContext)
                .getWritableDatabase();
        
    }
    
    //添加新的Crime
    public void addCrime(Crime c){
        ContentValues values =getContentValues(c);
        
        mDatebase.insert(CrimeTable.NAME,null,values);
    }
    //删除此Crime
    public void deleteCrime(Crime c){
        ContentValues values = getContentValues(c);
        
        mDatebase.delete(CrimeTable.NAME,null,null);
        
    }
    
    //返回数组列表
    public List<Crime>getCrimes(){
        
        List<Crime>crimes=new ArrayList<>();
        
        CrimeCursorWrapper cursor=queryCrimes(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }
    //返回带有指定ID的Crime对象
    public Crime getCrime(UUID id){
        CrimeCursorWrapper cursor=queryCrimes(
                CrimeTable.Cols.UUID+"=?",
                new String[]{id.toString()}
        );
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
    
    //数据库更新方法
    public void updateCrime(Crime crime){
        String uuidString=crime.getId().toString();
        ContentValues values = getContentValues(crime);
        
        mDatebase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID+"=?",
                new String[]{uuidString});
    }
    
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved()?1:0);
        values.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return values;
    }
    
    //查询CrimeTable中的记录
    private CrimeCursorWrapper queryCrimes(String whereClause, String[]whereArgs){
        Cursor cursor=mDatebase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
    
    
}
