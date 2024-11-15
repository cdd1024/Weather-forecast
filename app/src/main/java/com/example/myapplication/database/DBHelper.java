package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.entity.User;


import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private final static String CREATE_TABLE_USER = "create table t_user(" +
            "username varchar(200)," +
            "password varchar(200),"+
            "age integer,"+
            "sex varchar(10),"+
            "phone varchar(20),"+
            "email varchar(200)"+
            ")";

    public DBHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBHelper.CREATE_TABLE_USER);
        initData();
        for(User user:usersList){
            ContentValues values = new ContentValues();
            values.put("username",user.getUsername());
            values.put("password",user.getPassword());
            values.put("age",user.getAge());
            values.put("sex",user.getSex());
            values.put("phone",user.getPhone());
            values.put("email",user.getEmail());
            sqLiteDatabase.insert("t_user",null,values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private List<User> usersList;
    public void initData(){
        usersList = new ArrayList<>();
        usersList.add(new User("zhangsan","123456",22,"男","18040024521","zhangsan@163.com"));
        usersList.add(new User("lisi","123456",23,"女","18040024522","lisi@163.com"));
        usersList.add(new User("wangwu","123456",24,"男","18040024523","wangwu@163.com"));
    }

}
