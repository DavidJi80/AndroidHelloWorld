package com.github.davidji80.helloworld;

import android.app.Application;
import com.github.davidji80.helloworld.dao.SQLiteDbHelper;

public class HelloWorldApplication extends Application {
    private SQLiteDbHelper helper;

    public SQLiteDbHelper getSQLiteDbHelper(){
        if(helper==null){
            helper=new SQLiteDbHelper(this);
        }
        return helper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSQLiteDbHelper().create();
    }
}
