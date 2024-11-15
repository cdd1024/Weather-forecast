package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.entity.User;

public class UserDao {
    //查询指定用户名的用户信息。
    public static User queryUserInfo(Context context, String musicName) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("t_user",null,"username=?",new String[]{musicName},null,null,null);
        User user = null;
        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            user = new User(username,password,age,sex,phone,email);
        }
        db.close();
        return user;
    }

    public static void addUser(Context context , User  user){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("age",user.getAge());
        values.put("sex",user.getSex());
        values.put("phone",user.getPhone());
        values.put("email",user.getEmail());
        db.insert("t_user",null,values);
        db.close();
    }
}
