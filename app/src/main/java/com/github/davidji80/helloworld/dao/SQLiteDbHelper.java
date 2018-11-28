package com.github.davidji80.helloworld.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    //定义数据库文件名称和数据库版本，系统发现当数据库版本DB_VERSION的值变化了就会自动更新数据库
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

    /**
     * 构造函数
     *
     * @param context
     */
    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * onCreate方法接受SQLiteDatabase类型的单个参数，是重写父类的对应方法。
     * 它在类创建时会自动执行，如果数据库不存在的话创建数据库。
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        // 创建 students 表
        db.execSQL(StudentDAO.CREATE_TABLE_SQL);
    }


    /**
     * 数据库中表的结构改变是软件维护中经常遇到的事情。
     * 当我们数据库结构更新的时候，版本号变量DB_VERSION增加时，Android就自动执行onUpgrade方法。
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                break;

            default:
                break;
        }
    }

    /**
     * 连接数据库
     * @return
     */
    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    public void create() {
        open();
    }
}
