package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.UserDao;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    public void initView(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String passwordd = etPassword.getText().toString();
                User userInSQLite = UserDao.queryUserInfo(LoginActivity.this,username);
                if(userInSQLite != null && userInSQLite.getPassword().equals(passwordd)){
                    SharedPreferences sp = LoginActivity.this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("loginUser",username);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,ChooseActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}