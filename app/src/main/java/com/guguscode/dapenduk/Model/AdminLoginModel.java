package com.guguscode.dapenduk.Model;

import android.text.TextUtils;

import com.guguscode.dapenduk.Presenter.AdminLoginPresenter;
import com.guguscode.dapenduk.View.AdminLoginView;

public class AdminLoginModel implements AdminLoginPresenter {

    AdminLoginView adminLoginView;

    public AdminLoginModel(AdminLoginView adminLoginView){
        this.adminLoginView = adminLoginView;
    }
    @Override
    public void runLogin(String username, String password) {
        if (TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
            adminLoginView.loginValid();
        }else {
            if (username.equals("admin")&& password.equals("admin")){
                adminLoginView.loginSuccess();
            }else {
                adminLoginView.loginError();
            }
        }
    }
}
