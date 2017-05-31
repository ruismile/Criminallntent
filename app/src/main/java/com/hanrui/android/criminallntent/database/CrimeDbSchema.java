package com.hanrui.android.criminallntent.database;

/**
 * @author
 * @version 1.0
 * @date 2017/5/31 0031
 */

public class CrimeDbSchema {
    //定义描述数据表元素的String常量
    public static final class CrimeTable{
        //定义数据库表名
        public static final String NAME="crimes";
        
        //定义数据表字段
        public static final class Cols{
            public static final String UUID="uuid";
            public static final String TITLE="title";
            public static final String DATE="date";
            public static final String SOLVED="solved";
            public static final String SUSPECT="suspect";
        }
    }
    
    
}
