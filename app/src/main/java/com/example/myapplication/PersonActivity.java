package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.database.UserDao;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvAge;
    private TextView tvSex;
    private TextView tvPhone;
    private TextView tvEmail;
    private Button btnLogout;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initView();
        initData();
        bindData();
    }

    public void initView(){
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        btnLogout = (Button) findViewById(R.id.btn_logout);
    }

    public void initData(){
        SharedPreferences sp = this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String username = sp.getString("loginUser","");
        user = UserDao.queryUserInfo(this,username);
    }

    public void bindData(){
        tvEmail.setText(user.getEmail());
        tvAge.setText(user.getAge()+"");
        tvName.setText(user.getUsername());
        tvSex.setText(user.getSex());
        tvPhone.setText(user.getPhone());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = getBaseContext().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("loginUser");
                Intent intent = new Intent(PersonActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}